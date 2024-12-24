package com.example.hci.serviceImpl;

import com.example.hci.enums.PoetryLevelEnum;
import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.exception.HCIException;
import com.example.hci.po.Poetry;
import com.example.hci.po.User;
import com.example.hci.repository.PoetryRepository;
import com.example.hci.repository.SentenceRepository;
import com.example.hci.repository.UserRepository;
import com.example.hci.service.PoetryService;
import com.example.hci.util.SecurityUtil;
import com.example.hci.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.hci.po.Sentence;

@Service
public class PoetryServiceImpl implements PoetryService {
    @Autowired
    PoetryRepository poetryRepository;
    @Autowired
    SentenceRepository sentenceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityUtil securityUtil;

    @Override
    public List<PoetryVO> getAllPoetry(){
        List<Poetry> poetryList = poetryRepository.findAll();
        List<PoetryVO> poetryVOList = new ArrayList<>();
        for (Poetry poetry : poetryList) {
            poetryVOList.add(poetry.toVO());
        }
        return poetryVOList;
    }

    @Override
    public PoetryVO getPoetry(Long id) {
        Poetry poetry = poetryRepository.findById(id).orElse(null);
        if (poetry == null) {
            throw HCIException.DIYException("诗歌不存在");
        }
        return poetry.toVO();
    }

    @Override
    public List<PoetryVO> getPoetryByLevel(PoetryLevelEnum level, Integer size) {
        List<Poetry> poetryList = poetryRepository.findByLevel(level);
        return getRandomPoetryList(poetryList, size);
    }

    @Override
    public List<PoetryVO> getPoetryByLevelAndType(PoetryLevelEnum level, PoetryTypeEnum type, Integer size) {
        List<Poetry> poetryList = poetryRepository.findByLevelAndType(level, type);
        return getRandomPoetryList(poetryList, size);
    }

    @Override
    public List<SentenceVO> getPoetrySentence(Long id) {
        return sentenceRepository.findByPoetryId(id).stream()
                .map(Sentence::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean favPoetry(Long id) {
        User user = securityUtil.getCurrentUser();
        Poetry poetry = poetryRepository.findById(id).orElse(null);
        if (user.getFavPoetry().contains(id)) {
            throw HCIException.DIYException("已经收藏过了");
        }
        if (poetry == null) {
            throw HCIException.DIYException("诗歌不存在");
        }
        user.getFavPoetry().add(id); // 添加到收藏夹中
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean unfavPoetry(Long id) {
        User user = securityUtil.getCurrentUser();
        Poetry poetry = poetryRepository.findById(id).orElse(null);
        if (poetry == null) {
            return false; // 如果诗歌不存在，返回失败
        }
        user.getFavPoetry().remove(id); // 从收藏夹中移除
        userRepository.save(user);
        return true;
    }

    @Override
    public List<PoetryVO> getFavPoetry() {
        User user = securityUtil.getCurrentUser();
        List<Poetry> poetryList = poetryRepository.findByIdIn(user.getFavPoetry());
        return poetryList.stream()
                .map(Poetry::toVO)
                .collect(Collectors.toList());
    }

//    @Override
//    public GridContentVO getGridContent(GridRequest gridRequest) {
//        Poetry poetry = getPoetry(gridRequest.getPoetryId()).toPO();
//        int gridSize = gridRequest.getGridSize();
//        ArrayList<Character> grid = new ArrayList<>();
//        List<PoetryVO> poetryList = getPoetryByLevel(poetry.getLevel(),5);
//        for(PoetryVO poet:poetryList)
//    }

    public List<PoetryVO> getRandomPoetryList(List<Poetry> poetryList, Integer size) {
        if (poetryList.size() < size) {
            throw HCIException.DIYException("诗歌数量不足");
        }
        // 从查询结果中提取前 size 个元素
        // 打乱列表
        Collections.shuffle(poetryList);
        List<Poetry> selectedPoetries = poetryList.subList(0, size); // 获取前 size 个元素

        return selectedPoetries.stream()
                .map(Poetry::toVO)
                .collect(Collectors.toList());
    }

    public List<PoetryVO> searchPoetry(String keyword) {
        List<Poetry> poetryList = poetryRepository.findByTitleContainingOrAuthor(keyword,keyword);
        List<Sentence> sentenceList = sentenceRepository.findByPrevOrNext(keyword,keyword);
        for(Sentence sentence:sentenceList){
            if(poetryList.contains(sentence.getPoetry())) continue;
            poetryList.add(sentence.getPoetry());
        }
        if (poetryList.isEmpty()) {
            throw HCIException.DIYException("没有找到相关的诗歌");
        }
        return poetryList.stream()
                .map(Poetry::toVO)
                .collect(Collectors.toList());
    }


}

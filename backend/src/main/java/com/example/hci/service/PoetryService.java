package com.example.hci.service;

import com.example.hci.enums.PoetryLevelEnum;
import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.vo.GridContentVO;
import com.example.hci.vo.GridRequest;
import com.example.hci.vo.PoetryVO;
import com.example.hci.vo.SentenceVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PoetryService {
    PoetryVO getPoetry(Long id);

    List<PoetryVO> getPoetryByLevel(PoetryLevelEnum level, Integer size);

    List<PoetryVO> getPoetryByLevelAndType(PoetryLevelEnum level, PoetryTypeEnum type, Integer size);

    List<SentenceVO> getPoetrySentence(Long id);

//     GridContentVO getGridContent(GridRequest gridRequest);
}

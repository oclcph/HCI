package com.example.hci.controller;

import com.example.hci.enums.PoetryLevelEnum;
import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.service.PoetryService;
import com.example.hci.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poetry")
public class PoetryController {
    @Autowired
    PoetryService poetryService;

    @GetMapping("/{id}")
    public ResultVO<PoetryVO> getPoetry(@PathVariable(value = "id") Long id) {
        return ResultVO.buildSuccess(poetryService.getPoetry(id));
    }
    @GetMapping("/{id}/sentence")
    public ResultVO<List<SentenceVO>> getPoetrySentence(@PathVariable(value = "id") Long id) {
        return ResultVO.buildSuccess(poetryService.getPoetrySentence(id));
    }
    @PostMapping("/{id}/fav")
    public ResultVO<Boolean> addToFav(@PathVariable(value = "id") Long id){
        return ResultVO.buildSuccess( poetryService.favPoetry(id));
    }
    @PostMapping("/{id}/unfav")
    public ResultVO<Boolean> removeFromFav(@PathVariable(value = "id") Long id){
        return ResultVO.buildSuccess( poetryService.unfavPoetry(id));
    }
    @GetMapping("/fav/get")
    public ResultVO<List<PoetryVO>> getFavPoetry(){
        return ResultVO.buildSuccess(poetryService.getFavPoetry());
    }


    @GetMapping("/level") // 通过诗歌等级获取指定数量的诗歌,随机打乱过了
    public ResultVO<List<PoetryVO>> getPoetryByLevel(@RequestParam("level") PoetryLevelEnum level, @RequestParam("size") Integer size) {
        return ResultVO.buildSuccess(poetryService.getPoetryByLevel(level, size));
    }

    @GetMapping("/level/type") //根据诗歌等级和类型获取指定数量的诗歌
    public ResultVO<List<PoetryVO>> getPoetryByLevelAndType(@RequestParam("level") PoetryLevelEnum level, @RequestParam("type") PoetryTypeEnum type, @RequestParam("size") Integer size) {
        return ResultVO.buildSuccess(poetryService.getPoetryByLevelAndType(level, type, size));
    }



//    @GetMapping("/grid")
//    public ResultVO<GridContentVO> getGridContent(@RequestBody GridRequest gridRequest){
//        return ResultVO.buildSuccess(poetryService.getGridContent(gridRequest));
//    }
}

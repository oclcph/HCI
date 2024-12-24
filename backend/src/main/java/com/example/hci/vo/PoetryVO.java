package com.example.hci.vo;

import com.example.hci.enums.PoetryLevelEnum;
import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.po.Poetry;
import com.example.hci.po.Sentence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PoetryVO {
    private Long id;
    private String title;
    private String author;
    private PoetryTypeEnum type; // 类型可以是五言、七言、其它。
    // 九宫格从五言中挑选、十二宫格从七言中挑选
    // 根据上句选下句直接从所有诗词里面拿Sentence

    private PoetryLevelEnum level;
    private String dynasty;
    private String content;
    public Poetry toPO() {
        Poetry poetry = new Poetry();
        poetry.setId(id);
        poetry.setAuthor(author);
        poetry.setType(type);
        poetry.setLevel(level);
        poetry.setTitle(title);
        poetry.setDynasty(dynasty);
        poetry.setContent(content);
        return poetry;
    }

}

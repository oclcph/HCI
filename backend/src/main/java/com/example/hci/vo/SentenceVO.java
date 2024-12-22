package com.example.hci.vo;

import com.example.hci.po.Poetry;
import com.example.hci.po.Sentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SentenceVO {

    private Long id;

    private String prev;

    private String next;

    private Poetry poetry;

    public Sentence toPO() {
        Sentence sentence = new Sentence();
        sentence.setId(id);
        sentence.setPrev(prev);
        sentence.setNext(next);
        sentence.setPoetry(poetry);
        return sentence;
    }

}

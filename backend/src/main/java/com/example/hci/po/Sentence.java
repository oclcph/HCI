package com.example.hci.po;

import com.example.hci.vo.SentenceVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Setter
public class Sentence {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "prev")
    private String prev;

    @Basic
    @Column(name = "next")
    private String next;

    @ManyToOne
    private Poetry poetry;

    public SentenceVO toVO() {
        SentenceVO sentenceVO = new SentenceVO();
        sentenceVO.setId(this.id);
        sentenceVO.setPrev(this.prev);
        sentenceVO.setNext(this.next);
        return sentenceVO;
    }
}

package com.example.hci.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Setter
public class Sentence {
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
}

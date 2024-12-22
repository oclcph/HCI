package com.example.hci.po;

import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.enums.PoetryLevelEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Setter
public class Poetry {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "dynasty")
    private String dynasty;


    @Basic
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private PoetryLevelEnum level;

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PoetryTypeEnum type;

}

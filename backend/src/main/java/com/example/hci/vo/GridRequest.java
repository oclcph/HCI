package com.example.hci.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GridRequest {
    private long poetryId;
    private String sentence;
    private int gridSize; // 九宫格还是十二宫格
}

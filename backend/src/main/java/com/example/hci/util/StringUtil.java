package com.example.hci.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtil {
    public static String shuffleString(String input) {
        // 将字符串转换为字符列表
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }

        // 打乱字符列表
        Collections.shuffle(characters);

        // 将打乱后的字符列表转换回字符串
        StringBuilder shuffledString = new StringBuilder();
        for (char c : characters) {
            shuffledString.append(c);
        }

        return shuffledString.toString();
    }
}

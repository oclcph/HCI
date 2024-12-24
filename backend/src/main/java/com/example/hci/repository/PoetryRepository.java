package com.example.hci.repository;

import com.example.hci.enums.PoetryLevelEnum;
import com.example.hci.enums.PoetryTypeEnum;
import com.example.hci.po.Poetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PoetryRepository extends JpaRepository<Poetry, Long> {
    List<Poetry> findByLevel(PoetryLevelEnum level);

    List<Poetry> findByLevelAndType(PoetryLevelEnum level, PoetryTypeEnum type);

    List<Poetry> findByIdIn(List<Long> favPoetry);

    List<Poetry> findByTitleContainingOrAuthor(String keyword, String author);
}

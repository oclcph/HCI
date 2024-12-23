package com.example.hci.repository;

import com.example.hci.po.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface SentenceRepository extends JpaRepository<Sentence, Integer> {
    List<Sentence> findByPoetryId(Long id);
}

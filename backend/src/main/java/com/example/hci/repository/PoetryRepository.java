package com.example.hci.repository;

import com.example.hci.po.Poetry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoetryRepository extends JpaRepository<Poetry, Integer> {
}

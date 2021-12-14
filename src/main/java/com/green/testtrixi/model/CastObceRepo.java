package com.green.testtrixi.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CastObceRepo extends JpaRepository<CastObce, Long> {

    List<CastObce> getCastObcesByObecId(Long id);
}

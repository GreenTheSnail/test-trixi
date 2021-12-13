package com.green.testtrixi.service;

import com.green.testtrixi.model.Obec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObecService {

    void save(Obec obec);

    List<Obec> findAll();

    Obec findById(long id);
}

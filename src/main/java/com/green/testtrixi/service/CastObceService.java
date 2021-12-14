package com.green.testtrixi.service;

import com.green.testtrixi.model.CastObce;
import com.green.testtrixi.model.Obec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CastObceService {

    void save(CastObce castObce);

    List<CastObce> getAllByObecId(Long id);

}

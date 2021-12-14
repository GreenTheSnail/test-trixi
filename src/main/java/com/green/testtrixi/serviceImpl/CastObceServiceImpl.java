package com.green.testtrixi.serviceImpl;

import com.green.testtrixi.model.CastObce;
import com.green.testtrixi.model.CastObceRepo;
import com.green.testtrixi.service.CastObceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CastObceServiceImpl implements CastObceService {

    private final CastObceRepo repository;

    @Autowired
    private CastObceServiceImpl(CastObceRepo repository) {
        this.repository = repository;
    }

    @Override
    public void save(CastObce castObce) {
        repository.save(castObce);
    }

    @Override
    public List<CastObce> getAllByObecId(Long id) {
        return repository.getCastObcesByObecId(id);
    }


}

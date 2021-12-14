package com.green.testtrixi.serviceImpl;

import com.green.testtrixi.model.Obec;
import com.green.testtrixi.model.ObecRepo;
import com.green.testtrixi.service.ObecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObecServiceImpl implements ObecService {

    private final ObecRepo repository;

    @Autowired
    private ObecServiceImpl(ObecRepo repository) {
        this.repository = repository;
    }

    @Override
    public void save(Obec obec) {
        repository.save(obec);
    }

    @Override
    public List<Obec> findAll() {
        return repository.findAll();
    }

    @Override
    public Obec findById(long id) {
        return repository.getById(id);
    }

}

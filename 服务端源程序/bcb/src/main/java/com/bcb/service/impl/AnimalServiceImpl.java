package com.bcb.service.impl;

import com.bcb.mapper.AnimalMapper;
import com.bcb.pojo.Animal;
import com.bcb.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    AnimalMapper animalMapper;
    @Override
    public Animal animals(String kind) {
        return animalMapper.animals(kind);
    }
}

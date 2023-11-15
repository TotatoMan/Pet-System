package com.bcb.service.impl;

import com.bcb.mapper.IdenMapper;
import com.bcb.pojo.IdeAnimal;
import com.bcb.service.IdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class IdeServiceImpl implements IdeService {
    @Autowired
    private IdenMapper idenMapper;
    @Override
    public void store(IdeAnimal ideAnimal)  {
        idenMapper.store(ideAnimal);
    }

    @Override
    public IdeAnimal check(LocalDateTime time) {
        IdeAnimal ideAnimal=idenMapper.check(time);
        return ideAnimal;
    }
}

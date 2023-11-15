package com.bcb.service;

import com.bcb.pojo.IdeAnimal;

import java.text.ParseException;
import java.time.LocalDateTime;

public interface IdeService {
    void store(IdeAnimal ideAnimal);
    IdeAnimal check(LocalDateTime time);
}

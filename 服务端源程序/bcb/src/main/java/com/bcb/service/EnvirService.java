package com.bcb.service;

import com.bcb.pojo.Envir;
import com.bcb.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EnvirService {
    //public List<Envir> envirlist();
    PageBean page(Integer page,Integer pageSize);
    Envir GetbyCount(Integer count);
    Envir GetbyTime(LocalDateTime updateTime);
    Envir GetbyDate(LocalDate update);
}

package com.bcb.service.impl;

import com.bcb.mapper.EnvirMapper;
import com.bcb.pojo.Envir;
import com.bcb.pojo.PageBean;
import com.bcb.service.EnvirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvirServiceImpl implements EnvirService {
    @Autowired
    private EnvirMapper envirMapper;

    /*@Override
    public List<Envir> envirlist() {
        List<Envir> envirList=envirMapper.envirlist();
        return envirList;
    }
*/
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //获取总记录数
        Long count=envirMapper.count();
        //获取分页查询结果列表
        Integer start=(page-1)*pageSize;
        List<Envir> envirList=envirMapper.page(start,pageSize);
        //封装PageBean对象
        PageBean pageBean=new PageBean(count,envirList);
        return pageBean;
    }

    @Override
    public Envir GetbyCount(Integer count) {
        Envir envir=envirMapper.GetbyCount(count);
        return envir;
    }

    @Override
    public Envir GetbyTime(LocalDateTime updateTime) {
        Envir envir=envirMapper.GetbyTime(updateTime);
        return envir;
    }
    public Envir GetbyDate(LocalDate update){
        Envir envir=envirMapper.GetbyDate(update);
        return envir;
    }
}

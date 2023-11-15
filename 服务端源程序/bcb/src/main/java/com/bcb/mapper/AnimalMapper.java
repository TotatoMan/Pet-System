package com.bcb.mapper;

import com.bcb.pojo.Animal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AnimalMapper {
    @Select("select * from animals where kind=#{kind}")
    public Animal animals(String kind);
}

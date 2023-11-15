package com.bcb.mapper;

import com.bcb.pojo.Envir;
import com.bcb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EnvirMapper {
    /*@Select("select * from envir")
    List<Envir> envirlist();*/
    //查询总记录数
    @Select("select count(*) from envirs")
    public Long count();
    //分页查询获取列表数据
    @Select("select * from envirs limit #{start},#{pageSize}")
    public List<Envir> page(Integer start,Integer pageSize);
    @Select("select * from envirs where Count=#{count}")
    public Envir GetbyCount(Integer count);
    @Select("select * from envirs where Time=#{updateTime}")
    public Envir GetbyTime(LocalDateTime updateTime);
    @Select("SELECT * FROM envirs\n" +
            "WHERE Count=(SELECT MAX(Count) FROM envirs\n" +
            "WHERE Time Like '${update}%')")
    public  Envir GetbyDate(LocalDate update);
}

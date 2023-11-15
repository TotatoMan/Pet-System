package com.bcb.mapper;

import com.bcb.pojo.IdeAnimal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface IdenMapper {
    @Insert("insert into ideanimal (url,kind) values ('${url}','${kind}')")
    void store(IdeAnimal ideAnimal);
    //@Select("select * from ideanimal where time=#{time}")
    @Select("SELECT * FROM `ideanimal` \n" +
            "WHERE id=(SELECT MAX(id) FROM ideanimal )")
    IdeAnimal check(LocalDateTime time);
}

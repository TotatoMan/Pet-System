package com.bcb.mapper;

import com.bcb.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    public User login(User user);
    @Select("select * from user where username=#{username}")
    public User register(User user);
    @Insert("Insert into user (username,password) value(#{username},#{password})")
    public void insert(User user);
    /*@Delete("delete from user where id=#{id}")
    public void delete(Integer id);*/
}

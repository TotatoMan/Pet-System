package com.bcb.mapper;

import com.bcb.pojo.EnvirThres;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnvirThresMapper {
    @Select("select * from envirthres")
    List<EnvirThres> threslist();
    @Select("select * from envirthres where id=#{id}")
    EnvirThres Getbyid(Integer id);
    /*@Update("UPDATE envirthres SET CO2thres=#{CO2thres},CH2Othres=#{CH2Othres}," +
            "TVOCthres=#{TVOCthres},PM25thres=#{PM25thres},PM10thres=#{PM10thres},Temperaturethres=#{Temperaturethres}," +
            "Humiditythres=#{Humiditythres} where id=#{id}")*/
    void thresupdate(EnvirThres envirThres);

}

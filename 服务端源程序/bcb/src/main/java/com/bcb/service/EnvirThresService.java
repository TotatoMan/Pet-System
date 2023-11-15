package com.bcb.service;

import com.bcb.pojo.EnvirThres;

import java.util.List;

public interface EnvirThresService {
    List<EnvirThres> threslist();
    EnvirThres GetbyId(Integer id);
    void thresupdate(EnvirThres envirThres);
}

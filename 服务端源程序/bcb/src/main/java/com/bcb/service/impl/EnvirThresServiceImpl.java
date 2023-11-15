package com.bcb.service.impl;

import com.bcb.mapper.EnvirThresMapper;
import com.bcb.pojo.EnvirThres;
import com.bcb.service.EnvirThresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnvirThresServiceImpl implements EnvirThresService {
    @Autowired
    private EnvirThresMapper envirThresMapper;
    @Transactional
    @Override
    public List<EnvirThres> threslist() {
        List<EnvirThres>  envirThresList=envirThresMapper.threslist();
        return envirThresList;
    }

    @Override
    public EnvirThres GetbyId(Integer id) {
        EnvirThres envirThres=envirThresMapper.Getbyid(id);
        return envirThres;
    }

    @Override
    public void thresupdate(EnvirThres envirThres) {

        envirThresMapper.thresupdate(envirThres);
    }
}

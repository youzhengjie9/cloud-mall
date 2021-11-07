package com.boot.service.impl;

import com.boot.dao.BrandMapper;
import com.boot.pojo.Brand;
import com.boot.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> selectAllBrand() {
        return brandMapper.selectAllBrand();
    }

    @Override
    public long selectBrandIdNameByPid(long pid) {
        return brandMapper.selectBrandIdNameByPid(pid);
    }

    @Override
    public Brand selectBrandByid(long bid) {
        return brandMapper.selectBrandByid(bid);
    }



}

package com.boot.dao;

import com.boot.pojo.RecommandImg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MxdpMapper {

    List<RecommandImg> selectMxdp();

}

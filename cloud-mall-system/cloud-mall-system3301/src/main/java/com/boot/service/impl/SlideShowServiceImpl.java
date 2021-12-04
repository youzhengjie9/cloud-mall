package com.boot.service.impl;

import com.boot.dao.SlideShowMapper;
import com.boot.pojo.SlideShow;
import com.boot.service.SlideShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class SlideShowServiceImpl implements SlideShowService {

    @Autowired
    private SlideShowMapper slideShowMapper;

    @Override
    public List<SlideShow> selectSlideShow() {
        return slideShowMapper.selectSlideShow();
    }

    @Override
    public List<SlideShow> selectAllSlideShowByLimit(int page, int limit) {
        return slideShowMapper.selectAllSlideShowByLimit(page, limit);
    }

    @Override
    public int selectSlideShowCount() {
        return slideShowMapper.selectSlideShowCount();
    }

    @Override
    public void updateSort(long id, int sort) {
        slideShowMapper.updateSort(id, sort);
    }

    @Override
    public void deleteSlideShow(long id) {
        slideShowMapper.deleteSlideShow(id);
    }

    @Override
    public void batchDeleteSlideShow(long[] ids) {

        if(ids!=null&&ids.length>0)
        {

           for (long id : ids) {
               slideShowMapper.deleteSlideShow(id);
           }
        }
    }
}

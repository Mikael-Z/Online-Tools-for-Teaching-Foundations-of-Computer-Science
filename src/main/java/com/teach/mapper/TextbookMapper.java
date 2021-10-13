package com.teach.mapper;

import com.teach.pojo.Textbook;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TextbookMapper {

    //查询全部图书
    List<Textbook> findTextbookList(String title);

}

package com.teach.mapper;

import com.teach.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GradeExamMapper {

    //查询第一条未修改的答案
    Answer gradeAnswerInfo();

    void updateBatchNumber(String answerId);
}

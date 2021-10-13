package com.teach.mapper;

import com.teach.pojo.Answer;
import com.teach.pojo.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface ExamMapper {

    //查询全部的考试题目
    Set<Exam> findExamSet();

    //添加答案
    void addAnswer(Answer answer);
}

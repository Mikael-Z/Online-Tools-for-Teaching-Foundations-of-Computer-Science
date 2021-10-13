package com.teach.mapper;

import com.teach.pojo.Exercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExerciseMapper {
    //查询全部答案
    List<Exercise> findAnswer(String title);
}

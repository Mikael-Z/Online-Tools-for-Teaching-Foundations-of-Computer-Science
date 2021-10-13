package com.teach.mapper;

import com.teach.pojo.Score;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScoreMapper {

    //插入学生分数
    void addScore(Score score);

    //查询学生的全部成绩
    List<Score> findAllGrade(String userId);

    //查询前五条学生成绩
    List<Score> findFiveGrade(String userId);

}

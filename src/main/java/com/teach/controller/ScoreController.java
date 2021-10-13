package com.teach.controller;

import com.teach.mapper.GradeExamMapper;
import com.teach.mapper.ScoreMapper;
import com.teach.pojo.Score;
import com.teach.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ScoreController {
    @Autowired
    private GradeExamMapper gradeExamMapper;
    @Autowired
    private ScoreMapper scoreMapper;

    @RequestMapping("/submitScore")
    public String submitScore(@RequestParam("userId") String userId,
                              @RequestParam("teacherName") String teacherName,
                              @RequestParam("totalScore") String totalScore,
                              @RequestParam("teacherComment") String teacherComment,
                              @RequestParam("answerId") String answerId){

        Score score = new Score();

        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,15);
        score.setId(uuid);

        score.setUserId(userId);
        score.setTeacherName(teacherName);
        score.setTotalScore(totalScore);
        score.setTeacherComment(teacherComment);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String createDate = df.format(new Date());
        score.setCreateDate(createDate);

        scoreMapper.addScore(score);

        //修改batchNumber
        gradeExamMapper.updateBatchNumber(answerId);

        return "redirect:toGradeExamPage";

    }

    @RequestMapping("/toGradePage")
    public String toGradePage(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        String userId = user.getUserId();

        //查询全部的成绩
        List<Score> scoreList = scoreMapper.findAllGrade(userId);
        session.setAttribute("scoreList", scoreList);
        return "page-grade";
    }
}

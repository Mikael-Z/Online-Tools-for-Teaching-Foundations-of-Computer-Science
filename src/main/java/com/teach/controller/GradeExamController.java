package com.teach.controller;

import com.teach.mapper.GradeExamMapper;
import com.teach.pojo.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class GradeExamController {

    @Autowired
    private GradeExamMapper gradeExamMapper;

    @RequestMapping("/toGradeExamPage")
    public String toGradeExamPage(HttpSession session){
        //查询第一条未判卷的答案
        Answer answerInfo = gradeExamMapper.gradeAnswerInfo();
        if(answerInfo==null){
            return "page-main";
        }else{
            session.setAttribute("answerInfo", answerInfo);
            return "page-gradeExam";
        }
    }

}

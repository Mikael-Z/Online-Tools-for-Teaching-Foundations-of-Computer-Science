package com.teach.controller;

import com.teach.mapper.ExerciseMapper;
import com.teach.pojo.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExerciseController {

    @Autowired
    private ExerciseMapper exerciseMapper;

    @RequestMapping("/toExercise")
    public String toExercise(HttpSession session){
        String title = "answer1";
        List<Exercise> answerList = exerciseMapper.findAnswer(title);
        session.setAttribute("answerList", answerList);
        return "page-exerciseOne";
    }
}

package com.teach.controller;

import com.teach.mapper.TextbookMapper;
import com.teach.pojo.Textbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TextbookController {
    @Autowired
    TextbookMapper textbookMapper;

    //跳转textbook页面
    @RequestMapping("/textbook")
    public String toLogicAndProof(HttpSession session, @RequestParam("title") String title){
        //查询图书
        List<Textbook> textbookList = textbookMapper.findTextbookList(title);
        session.setAttribute("textbookList", textbookList);
        if(title.equals("logic")){
            //跳转logic and proof页面
            return "page-logicAndProof";
        }else if(title.equals("sets")){
            //跳转Sets,Functions,Sequences,Sums,and Matrices页面
            return "page-sets";
        }else{
            return "page-main";
        }
    }
}

package com.teach.controller;

import com.teach.mapper.ExamMapper;
import com.teach.pojo.Answer;
import com.teach.pojo.Exam;

import com.teach.pojo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExamController {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value(value="classpath:static/json/Exam.json")
    private Resource resource;

    @RequestMapping("/toExam")
    public String toExam(HttpSession session) throws IOException, JSONException {

        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuffer message = new StringBuffer();
        String line = null;
        while((line = br.readLine()) != null) {
            message.append(line);
        }

        String defaultString = message.toString();
        JSONArray jsonArray = new JSONArray(defaultString);

        for(int i=0;i<jsonArray.length();i++){
            Exam exam = new Exam();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            exam.setId(jsonObject.getString("id"));
            exam.setTitle(jsonObject.getString("title"));
            exam.setType(jsonObject.getString("type"));
            redisTemplate.opsForSet().add("exam", exam.getTitle());
        }
        List<String> examList = new ArrayList<String>();
        for(int i=0;i<10;i++){
            examList.add(redisTemplate.opsForSet().pop("exam"));
        }
        session.setAttribute("examList", examList);

        return "page-exam";
    }

    //提交用户的答案
    @RequestMapping("/submitAnswer")
    public String submitAnswer(@RequestParam("title1") String title1,
                               @RequestParam("answer1") String answer1,
                               @RequestParam("title2") String title2,
                               @RequestParam("answer2") String answer2,
                               @RequestParam("title3") String title3,
                               @RequestParam("answer3") String answer3,
                               @RequestParam("title4") String title4,
                               @RequestParam("answer4") String answer4,
                               @RequestParam("title5") String title5,
                               @RequestParam("answer5") String answer5,
                               @RequestParam("title6") String title6,
                               @RequestParam("answer6") String answer6,
                               @RequestParam("title7") String title7,
                               @RequestParam("answer7") String answer7,
                               @RequestParam("title8") String title8,
                               @RequestParam("answer8") String answer8,
                               @RequestParam("title9") String title9,
                               @RequestParam("answer9") String answer9,
                               @RequestParam("title10") String title10,
                               @RequestParam("answer10") String answer10,
                               HttpSession session){

        Answer answerInfo = new Answer();
        answerInfo.setTitle1(title1);
        answerInfo.setAnswer1(answer1);
        answerInfo.setTitle2(title2);
        answerInfo.setAnswer2(answer2);
        answerInfo.setTitle3(title3);
        answerInfo.setAnswer3(answer3);
        answerInfo.setTitle4(title4);
        answerInfo.setAnswer4(answer4);
        answerInfo.setTitle5(title5);
        answerInfo.setAnswer5(answer5);
        answerInfo.setTitle6(title6);
        answerInfo.setAnswer6(answer6);
        answerInfo.setTitle7(title7);
        answerInfo.setAnswer7(answer7);
        answerInfo.setTitle8(title8);
        answerInfo.setAnswer8(answer8);
        answerInfo.setTitle9(title9);
        answerInfo.setAnswer9(answer9);
        answerInfo.setTitle10(title10);
        answerInfo.setAnswer10(answer10);

        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,15);
        answerInfo.setId(uuid);
        answerInfo.setBatchNumber("1");//未进行判卷操作

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String createDate = df.format(new Date());
        answerInfo.setCreateDate(createDate);

        User userInfo = (User) session.getAttribute("userInfo");
        answerInfo.setUserId(userInfo.getUserId());

        //数据插入
        examMapper.addAnswer(answerInfo);

        return "page-main";
    }
}

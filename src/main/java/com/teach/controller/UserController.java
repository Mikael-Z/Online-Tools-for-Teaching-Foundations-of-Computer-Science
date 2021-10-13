package com.teach.controller;

import com.teach.mapper.DictionaryMapper;
import com.teach.mapper.ScoreMapper;
import com.teach.mapper.UserMapper;
import com.teach.pojo.Dictionary;
import com.teach.pojo.Score;
import com.teach.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ScoreMapper scoreMapper;

    private User user = new User();

    //跳转登录页面
    @RequestMapping("/toLogin")
    public String goLogin(){
        return "page-login";
    }

    //跳转主页面
    @RequestMapping("/toMainPage")
    public String goMainPage(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        String userId = user.getUserId();
        List<Score> scoreList = scoreMapper.findFiveGrade(userId);
        List<Score> scoreFiveList = new ArrayList<Score>();
        if(scoreList.size()>5){
            for(int i=0;i<5;i++){
                scoreFiveList.addAll(scoreList);
            }
        }else{
            for(int i=0;i<scoreList.size();i++){
                scoreFiveList.add(scoreList.get(i));
            }
        }
        session.setAttribute("scoreFiveList", scoreFiveList);
        return "page-main";
    }

    //用户登录
    @RequestMapping("/findUserByAccountAndPassword")
    public String findUsers(@RequestParam("account") String userAccount, @RequestParam("password") String userPassword,
                            HttpSession session,
                            Model model){
        List<User> userInfo = userMapper.findUserList(userAccount, userPassword);
        int userSize = userInfo.size();
        if(userSize!=1){
            model.addAttribute("msg", "Incorrect account or password");
            return "page-login";
        }else{
            String createDate = userInfo.get(0).getCreateDate();
            createDate = createDate.split(" ")[0];
            userInfo.get(0).setCreateDate(createDate);

            session.setAttribute("userInfo", userInfo.get(0));

            //存放学龄
            //1.获取当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String recentDate = df.format(new Date());

            //2.计算日期差
            int recYear = Integer.parseInt(recentDate.split("-")[0]);
            int creYear = Integer.parseInt(createDate.split("-")[0]);

            int recMon = Integer.parseInt(recentDate.split("-")[1]);
            int creMon = Integer.parseInt(createDate.split("-")[1]);

            int recDay = Integer.parseInt(recentDate.split("-")[2]);
            int creDay = Integer.parseInt(createDate.split("-")[2]);

            int subYear = recYear - creYear;
            int subMon = recMon - creMon;
            int subDay = recDay - creDay;

            int result = 0;
            if(subYear!=0){
                if(subMon==0){
                    if(subDay<0){
                        subYear--;
                        result = subYear;
                    }
                }else{
                    result = subYear;
                }
            }
            session.setAttribute("subYear", subYear);

            //获取图书信息
            String dicType = "textbook";
            List<Dictionary> dicListForTextbook = findTextbookInfo(dicType);
            if(dicListForTextbook.size() == 0) {
                dicListForTextbook.get(0).setDicName("No data");
            }
            session.setAttribute("dicList",dicListForTextbook);
            String userId;
            User user = (User) session.getAttribute("userInfo");
            userId = user.getUserId();
            List<Score> scoreList = scoreMapper.findFiveGrade(userId);
            List<Score> scoreFiveList = new ArrayList<Score>();
            if(scoreList.size()>5){
                for(int i=0;i<5;i++){
                    scoreFiveList.add(scoreList.get(i));
                }
            }else{
                scoreFiveList.addAll(scoreList);
            }
            session.setAttribute("scoreFiveList", scoreFiveList);

            return "page-main";
        }
    }

    //跳转用户注册页面
    @GetMapping("/userRegister")
    public String userRegister(){
        return "page-register";
    }

    //用户注册
    @RequestMapping("/toRegister")
    public String saveUserInfo(@RequestParam("userName") String userName,
                               @RequestParam("userAccount") String account,
                               @RequestParam("nickName") String nickName,
                               @RequestParam("email") String email,
                               @RequestParam("userPassword") String password,
                               @RequestParam("userBirthday") String birthday,
                               @RequestParam("phoneNumber") String phoneNumber){

        //生成UUID
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,15);

        user.setUserId(uuid);
        user.setUserName(userName);
        user.setUserAccount(account);
        user.setNickName(nickName);
        user.setEmail(email);
        user.setUserPassword(password);
        user.setUserBirthday(birthday);
        user.setPhoneNumber(phoneNumber);
        user.setUserStatus("2");
        user.setAccountStatus("1");

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = df.format(new Date());
        user.setCreateDate(createDate);

        //新增用户
        userMapper.saveNewUserInfo(user);

        return "page-login";
    }

    //获取Textbook信息
    public List<Dictionary> findTextbookInfo(String dicType){
        int num = dictionaryMapper.findDicInfoNum();
        System.out.println(num);
        List<Dictionary> dicList = dictionaryMapper.findDicInfo(dicType);
        return dicList;
    }

    //跳转到用户信息界面
    @RequestMapping("/personInformation")
    public String toPersonInformation(){
        return "page-personalInformation";
    }

    //用户信息更新
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("userId") String userId,
                                 @RequestParam("userName") String userName,
                                 @RequestParam("nickName") String nickName,
                                 @RequestParam("userBirthday") String userBirthday,
                                 @RequestParam("email") String email,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        user.setUserId(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setUserBirthday(userBirthday);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        userMapper.updateUserInfo(user);

        session.setAttribute("userInfo", user);
        return "page-personalInformation";
    }

    //进入修改密码页面
    @RequestMapping("/updatePassword")
    public String updatePassword(HttpSession session){
        SimpleMailMessage message = new SimpleMailMessage();
        String verCodeSend = UUID.randomUUID().toString().substring(0,4).toUpperCase();
        User user = (User) session.getAttribute("userInfo");

        message.setFrom("1519761611@qq.com");
        message.setTo(user.getEmail());
        message.setSubject("Change Password");
        message.setText("Verification code: " + verCodeSend);
        try{
            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

        session.setAttribute("verCodeSend", verCodeSend);

        return "page-updatePassword";
    }

    //验证码确认
    @RequestMapping("/identifyingCode")
    public String identifyingCode(@RequestParam("verCode") String verCode, HttpSession session, Model model){
        String verCodeSend = (String) session.getAttribute("verCodeSend");
        if(verCode.equals(verCodeSend)){
            return "page-changePass";
        }else{
            model.addAttribute("msg","Verification code error");
            return "page-updatePassword";
        }
    }

    //修改密码
    @RequestMapping("/changePass")
    public String changePass(@RequestParam("userPassword") String userPassword,HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        user.setUserPassword(userPassword);
        userMapper.changePass(user);
        return "page-login";
    }
}

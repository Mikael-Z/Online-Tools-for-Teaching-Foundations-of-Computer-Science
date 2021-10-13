package com.teach.controller;

import com.teach.mapper.CommentMapper;
import com.teach.pojo.Comment;
import com.teach.pojo.Remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    //跳转评论页面
    @RequestMapping("/toCommentPage")
    public String toCommentPage(HttpSession session){
        //查询全部评论
        List<Comment> commentList = commentMapper.findAllComment();
        session.setAttribute("commentList", commentList);
        System.out.println(commentList.get(0));
        return "page-comment";
    }

    //跳转添加问题页面
    @RequestMapping("/toAddCommentPage")
    public String toAddCommentPage(){
        return "page-addComment";
    }

    //添加问题
    @RequestMapping("/addComment")
    public String addComment(@RequestParam("userId") String userId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("imgSrc") MultipartFile imgFile) throws IOException {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setTitle(title);
        comment.setContent(content);

        //文件上传
        if(imgFile.isEmpty()){
            System.out.println("文件为空");
        }
        //获取文件的原名
        String fileName = imgFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String filePath = "D://java//Demo1//src//main//resources//static//images//comment//";
        fileName = UUID.randomUUID().toString().substring(0, 10) + suffixName;
        File img = new File(filePath + fileName);

        imgFile.transferTo(img);

        String imgSrc = "images/comment/" + fileName;
        comment.setImgSrc(imgSrc);
        comment.setId(UUID.randomUUID().toString().substring(0, 8));
        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = df.format(new Date());
        comment.setCreateDate(createDate);

        commentMapper.addComment(comment);

        return "redirect:/toCommentPage";
    }

    //查看问题详情
    @RequestMapping("/commentDetail")
    public String commentDetail(@RequestParam("id") String id, HttpSession session){

        //查询评论
        Comment comment = new Comment();
        comment = commentMapper.findCommentById(id);
        session.setAttribute("comment", comment);


        //查询用户评论
        List<Remark> remarkList = commentMapper.findCommentDetail(id);
        session.setAttribute("remarkList", remarkList);

        return "page-commentDetail";
    }

    //添加用户评论
    @RequestMapping("/addUserComment")
    public String addUserComment(@RequestParam("userId") String userId,
                                 @RequestParam("commentId") String commentId,
                                 @RequestParam("content") String content){
        Remark remark = new Remark();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = df.format(new Date());

        remark.setId(UUID.randomUUID().toString().substring(0, 10));
        remark.setUserId(userId);
        remark.setCommentId(commentId);
        remark.setContent(content);
        remark.setCreateDate(createDate);

        commentMapper.addUserComment(remark);

        return "redirect:commentDetail?id="+commentId;
    }
}

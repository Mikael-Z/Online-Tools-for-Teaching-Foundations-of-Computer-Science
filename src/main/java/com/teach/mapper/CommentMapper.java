package com.teach.mapper;

import com.teach.pojo.Comment;
import com.teach.pojo.Remark;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> findAllComment();

    //添加评论
    void addComment(Comment comment);

    //根据id查询评论
    Comment findCommentById(String id);

    //查询评论详情
    List<Remark> findCommentDetail(String id);

    //添加用户评论
    void addUserComment(Remark remark);
}

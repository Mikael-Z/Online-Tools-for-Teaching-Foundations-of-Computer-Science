package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String id;
    private String userId;
    private String title;
    private String content;
    private String imgSrc;
    private String createDate;
    private User user;
}

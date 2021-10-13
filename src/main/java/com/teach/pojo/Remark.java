package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remark {
    private String id;
    private String userId;
    private String commentId;
    private String content;
    private String createDate;
    private User user;
}

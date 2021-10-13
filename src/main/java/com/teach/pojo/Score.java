package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String id;
    private String userId;
    private String teacherName;
    private String totalScore;
    private String teacherComment;
    private String createDate;
}

package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {
    private String id;
    private String title;
    private String option1;
    private String type;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String option6;
    private String option7;
}

package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Textbook {
    private String id;
    private String src;
    private String title;
    private String content;

}

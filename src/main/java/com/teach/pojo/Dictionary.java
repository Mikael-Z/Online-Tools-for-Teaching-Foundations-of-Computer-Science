package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary {
    private String id;
    private String dicCode;
    private String dicName;
    private String dicType;
    private String dicURL;
}

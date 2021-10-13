package com.teach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String userAccount;
    private String userName;
    private String nickName;
    private String userPassword;
    private String userBirthday;
    private String email;
    private String phoneNumber;
    private String userStatus;
    private String createDate;
    private String accountStatus;
    private List<Comment> commentList;
}

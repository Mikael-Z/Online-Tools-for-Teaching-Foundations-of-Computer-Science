package com.teach.mapper;

import com.teach.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //根据账号密码查询用户
    List<User> findUserList(String userAccount, String userPassword);

    //添加新的用户
    void saveNewUserInfo(User user);

    int findUserNum();

    //更新用户信息
    void updateUserInfo(User user);

    //更改密码
    void changePass(User user);

}

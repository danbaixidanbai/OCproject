package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private long userId;
    private String userLoginName;
    private String userPassword;
    private String userName;
    private String userImage;
    private int userType;
    private int userGender;
    private String userBirthday;
    private String userSign;
    private String userMobile;
    private Date userCreateTime;
    private Date userUpdateTime;
    private int del;

}

package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    private int gender;
    private String userBirthday;
    private String userSign;
    private String userMobile;
    private String userCreateTime;
    private String userUpdateTime;

}

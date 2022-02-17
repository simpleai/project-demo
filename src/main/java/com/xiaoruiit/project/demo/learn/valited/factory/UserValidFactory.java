package com.xiaoruiit.project.demo.learn.valited.factory;

import com.xiaoruiit.project.demo.learn.valited.UserValid;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
public class UserValidFactory {

    public static UserValid buildUserValid() {
        UserValid userValid = new UserValid();
        userValid.setUserId("1");
        return userValid;
    }
}

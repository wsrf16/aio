package com.aio.portable.park.test;

import com.aio.portable.park.postprocessor.UserInfoEntity;

public class BeanOrder {

    UserInfoEntity userInfoEntity4th;

    UserInfoEntity userInfoEntity2nd = new UserInfoEntity();

    public BeanOrder(UserInfoEntity userInfoEntity1st) {
        UserInfoEntity userInfoEntity3th = userInfoEntity1st;
    }
}

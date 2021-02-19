package com.mgame.service;

import com.mgame.dao.entity.UserInfoEntity;
import com.mgame.utils.DecodeUtils;
import org.springframework.util.ObjectUtils;

public interface IUserService {
    void saveUserInfo(String account, String password);

    void update(UserInfoEntity userInfoEntity);

    boolean verifyAccountAndPassword(String account, String password);

    boolean register(String account, String password);

    UserInfoEntity getUserInfo(String account);
}

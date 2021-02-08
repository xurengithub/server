package com.mgame.service;

import com.mgame.dao.entity.UserInfoEntity;
import com.mgame.dao.mapper.UserMapper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Sid sid;

    public void saveUserInfo(String account, String password) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(sid.nextShort());
        userInfoEntity.setNickName("");
        userInfoEntity.setAccount(account);
        userInfoEntity.setPassword(password);

        userMapper.saveOne(userInfoEntity);
    }

    public void update(UserInfoEntity userInfoEntity) {
        userMapper.updateOne(userInfoEntity);
    }

    public boolean verifyAccountAndPassword(String account, String password) {
        UserInfoEntity userInfoEntity = userMapper.queryUserByAccount(account);
        if(null != userInfoEntity && null != userInfoEntity.getAccount() && !"".equals(userInfoEntity.getAccount())) {
            if(userInfoEntity.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
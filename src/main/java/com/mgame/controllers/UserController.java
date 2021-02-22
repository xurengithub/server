package com.mgame.controllers;

import com.mgame.controllers.pojo.Users;
import com.mgame.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public IGameJSONResult login(@RequestBody Users user) {
        String account = user.getAccount();
        String password = user.getPassword();

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return IGameJSONResult.ok("用户名或密码不能为空...");
        }

        // 2. 判断用户是否存在
        boolean succ = userService.verifyAccountAndPassword(account, password);

        // 3. 返回
        if (succ) {
            return IGameJSONResult.ok();
        } else {
            return IGameJSONResult.errorMsg("用户名或密码不正确, 请重试...");
        }
    }

}

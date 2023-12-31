package com.jch.crm.settings.service;

import com.jch.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserByLoginActAndPwd(Map<String, Object> map);

    List<User> listAllUsers();
}

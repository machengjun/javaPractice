package com.example.saas.service;

import com.example.saas.entity.User;
import com.example.saas.spec.Saasable;

public interface UserService extends Saasable {
    User getMyFriend();

    void insertMyFriend(User user);
}

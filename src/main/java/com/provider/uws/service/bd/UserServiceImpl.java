package com.provider.uws.service.bd;

import com.provider.uws.model.User;
import com.provider.uws.repository.UserRepository;

public class UserServiceImpl extends BaseEntityServiceImpl<User> {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }
}

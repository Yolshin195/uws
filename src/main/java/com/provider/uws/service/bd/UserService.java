package com.provider.uws.service.bd;

import com.provider.uws.model.User;

public interface UserService extends BaseEntityService<User> {
    User findByUsername(String username);
}

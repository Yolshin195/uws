package com.provider.uws.service.bd;

import com.provider.uws.model.User;
import com.provider.uws.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseEntityServiceImpl<User> implements UserService {
    protected UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsernameAndDeleteTsIsNull(username);
    }
}

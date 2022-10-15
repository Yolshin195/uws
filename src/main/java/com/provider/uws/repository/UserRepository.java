package com.provider.uws.repository;

import com.provider.uws.model.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseEntityRepository<User> {
    User findByUsernameAndDeleteTsIsNull(String username);
}

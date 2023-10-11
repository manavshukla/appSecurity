package com.security.repo;

import com.security.dto.EmployeeDto;
import com.security.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);

    @Query(value = "{'userId': ?0,'status':?0}")
    int findStatusByUserId(String userId, Integer status);


}

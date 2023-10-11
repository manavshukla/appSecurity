package com.security.repo;

import com.security.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends MongoRepository<Role, String> {
    List<Role> findByStatus(Integer status);
}

package com.security.repo;

import com.security.model.JwtToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepo extends MongoRepository<JwtToken, ObjectId> {
    JwtToken findByToken(String token);

}

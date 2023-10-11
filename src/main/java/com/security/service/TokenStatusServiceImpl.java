package com.security.service;

import com.security.model.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TokenStatusServiceImpl implements TokenStatusService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TokenStatusServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public int getTokenStatus() {
        try {

            JwtToken token = mongoTemplate.findOne(Query.query(Criteria.where("id").is("status")), JwtToken.class);
            if (token != null) {
                return token.getStatus();
            }
            return 0;


        } catch (Exception e) {
            System.out.println(e);

        }
        return 0;
    }
}

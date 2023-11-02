package com.security.service;

import com.security.model.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final String SESSION_COLLECTION = "sessions";

    @Autowired
    private MongoTemplate mongoTemplate;


    public void setSessionData(String sessionId, String userId, String employeeName) {
        // Store session-like data in MongoDB
        SessionData sessionData = new SessionData(sessionId, userId, employeeName);
        mongoTemplate.save(sessionData, SESSION_COLLECTION);
    }

    public SessionData getSessionData(String sessionId) {
        // Retrieve session-like data from MongoDB based on sessionId
        return mongoTemplate.findById(sessionId, SessionData.class, SESSION_COLLECTION);
    }
}

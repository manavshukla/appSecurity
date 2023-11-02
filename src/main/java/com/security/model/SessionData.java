package com.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sessions")
public class SessionData {

    @Id
    private String id;
    private String sessionId;
    private String userId;
    private String employeeName;

    public SessionData(String sessionId, String userId, String employeeName) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.employeeName = employeeName;
    }


}

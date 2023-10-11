package com.security.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        try {

            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/appSecurity");
            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            return new MongoTemplate(MongoClients.create(mongoClientSettings), "appSecurity");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}


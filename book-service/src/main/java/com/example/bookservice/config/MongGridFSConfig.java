package com.example.bookservice.config;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class MongGridFSConfig {
    @Bean
    public GridFSBucket gridFsBucket(MongoDatabaseFactory mongoDatabaseFactory) {
        return GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }
}

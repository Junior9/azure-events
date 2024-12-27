package com.example.events.subscriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventBlobs {

    protected static Logger logger = LoggerFactory.getLogger(EventBlobs.class);

    @GetMapping("/event/blob")
    public ResponseEntity<String> addBlobEvent(){
        logger.info("[BLOB CREATED] ");
        return ResponseEntity.ok("New Blob created");
    }

    @PostMapping("/event/blob")
    public ResponseEntity<String> addBlobEvent1(){
        logger.info("[BLOB CREATED] ");
        return ResponseEntity.ok("New Blob created");
    }


}
package com.example.events.subscriptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventBlobs {

    @GetMapping("/event/blob")
    public ResponseEntity<String> addBlobEvent(){
        return ResponseEntity.ok("New Blob created");
    }

}
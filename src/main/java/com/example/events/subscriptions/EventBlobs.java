package com.example.events.subscriptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azure.cosmos.implementation.HttpConstants.StatusCodes;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.example.events.model.Event;
import com.example.events.repositories.CosmoDB;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EventBlobs {

    protected static Logger logger = LoggerFactory.getLogger(EventBlobs.class);
    private CosmoDB cosmoDb;

    @GetMapping("/event/blob")
    public ResponseEntity<CosmosPagedIterable<Object>> addBlobEvent(){
        try{
            logger.info("[BLOB CREATED] - Event save on cosmoDB ");
            CosmosPagedIterable<Object> result = this.cosmoDb.getEvents();
            return ResponseEntity.status(StatusCodes.OK).body(result);
        }catch(Exception e){
            return ResponseEntity.status(StatusCodes.BADREQUEST).body(null);
        }
        
    }

    @PostMapping("/event/blob")
    public ResponseEntity<String> addBlobEvent1(@RequestBody Event event){
        logger.info("[BLOB CREATED] ");
        this.cosmoDb.setEvents(event); 
        return ResponseEntity.ok("New Blob created");
    }



}
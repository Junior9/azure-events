package com.example.events.subscriptions;


import java.util.Optional;
import java.util.Random;

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
            Optional<CosmosPagedIterable<Object>> result = this.cosmoDb.getEvents();
            if(!result.isEmpty()){
                return ResponseEntity.status(StatusCodes.OK).body(result.get());
            }else{
                logger.error("[BLOB CREATED ERROR] ");
                return ResponseEntity.status(StatusCodes.BADREQUEST).body(null);
            }
        }catch(Exception e){
            logger.error("[BLOB CREATED ERROR] " + e.getMessage());
            return ResponseEntity.status(StatusCodes.BADREQUEST).body(null);
        }
        
    }

    @PostMapping("/event/blob")
    public ResponseEntity<String> addBlobEvent1(@RequestBody Event event){
        logger.info("[BLOB CREATED] ");
        this.cosmoDb.setEvents(event); 
        return ResponseEntity.ok("New Blob created");
    }


    @PostMapping("/event/created-blob")
    public ResponseEntity<String> addBlobEvent2(){
        try{
            logger.info("[BLOB CREATED] ");
            Random rand = new Random();
            int id = rand.nextInt(1000);
            this.cosmoDb.setEvents(Event.builder().id(Integer.toString(id)).type("blob").date("").build()); 
            return ResponseEntity.ok("New Blob created");
        }catch(Exception e){
            logger.error("[BLOB CREATED] " + e.getMessage());
            return ResponseEntity.status(StatusCodes.BADREQUEST).body("Error" + e.getMessage());
        }
       
    }


}
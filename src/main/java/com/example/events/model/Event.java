package com.example.events.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {

    private String id;
    private String type;
    private String date;

}

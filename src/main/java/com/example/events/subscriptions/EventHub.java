package com.example.events.subscriptions;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.azure.core.credential.TokenCredential;
import com.azure.cosmos.implementation.guava25.collect.Lists;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.SendOptions;
import com.example.events.common.KeyVault;
import com.example.events.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventHub {

    protected static Logger logger = LoggerFactory.getLogger(EventHub.class);

    public void newUserNotification(Event event){
        try {
            TokenCredential credential = new DefaultAzureCredentialBuilder().build();
            KeyVault keyVault = new KeyVault();

            String nameSpace = keyVault.getSecret("eventhubhostsecret");

		    EventHubProducerClient producer = new EventHubClientBuilder()
			.credential(nameSpace,
			"newusers",
			credential)
			.buildProducerClient();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String userJson = ow.writeValueAsString(event);
            EventData event_hub = new EventData(userJson);
            SendOptions sendOptions = new SendOptions().setPartitionKey("id");
            producer.send(Lists.newArrayList(event_hub), sendOptions);
            logger.info("Event [useer-created] was send");
        } catch (JsonProcessingException e) {
            logger.error("Event error [useer-created] was not send, error: " + e.getMessage());
        }
    
    }

}

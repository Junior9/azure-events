package com.example.events.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.example.events.common.KeyVault;
import com.example.events.model.Event;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CosmoDB {

    private final String databaseName = "az204";
    private final String containerName = "events";
    private KeyVault keyVaultSecrets;
    protected static Logger logger = LoggerFactory.getLogger(CosmoDB.class);


    private CosmosContainer getContainer() throws Exception {
        CosmosDatabase database;
        CosmosClient client;
        keyVaultSecrets = new KeyVault();

        ArrayList<String> preferredRegions = new ArrayList<String>();
        preferredRegions.add("West US");

        String cosmoDbkey  = keyVaultSecrets.getSecret("cosmodb204key");
        String cosmoDbHost  = keyVaultSecrets.getSecret("cosmodbhost");

        new DefaultAzureCredentialBuilder().build();

        //  Create sync client
        client = new CosmosClientBuilder()
            .endpoint(cosmoDbHost)
            .key(cosmoDbkey)
            .preferredRegions(preferredRegions)
            .userAgentSuffix("CosmosDBJavaQuickstart")
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();


        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(databaseName);
        database = client.getDatabase(databaseResponse.getProperties().getId());
        
           //  Create container if not exists
        CosmosContainerProperties containerProperties =
            new CosmosContainerProperties(containerName, "/partitionKey");

        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);

        return database.getContainer(containerResponse.getProperties().getId());
    }


    public Optional<CosmosPagedIterable<Object>> getEvents() {

        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        
        try{
            CosmosContainer container = this.getContainer();

            CosmosPagedIterable<Object> result = container.queryItems(
                "SELECT * FROM c", queryOptions, Object.class);
    
            return Optional.of(result);

        }catch(Exception e){
            logger.error("ERROR database : ", e);
            return Optional.empty();
        }
       
    }


    public CosmosItemResponse<Event> setEvents(Event event) {

        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        try{
            CosmosContainer container = this.getContainer();
            CosmosItemResponse<Event> result = container.createItem(event);
            return result;
        }catch(Exception e){
            logger.error("ERROR database : ", e);
            return null;
        }
  
    }

}

package com.example.events.subscriptions;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.events.common.KeyVault;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UploadService {

    private KeyVault keyVault;

    public String uploadBlob(MultipartFile data) {
        try {
            String blobName = data.getOriginalFilename();
            BlobServiceClient clientBlob = this.getBlobClient();
            BlobClient storageBlobClient = clientBlob.getBlobContainerClient("imagine").getBlobClient(blobName);
            storageBlobClient.upload(data.getInputStream(),data.getSize(), true);
            String url = storageBlobClient.getBlobUrl();
            return url;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    private BlobServiceClient getBlobClient(){
        this.keyVault = new KeyVault();
        return new BlobServiceClientBuilder().connectionString(this.keyVault.getSecret("stgcontainerimg")).buildClient();
    }



}

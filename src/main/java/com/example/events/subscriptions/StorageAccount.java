package com.example.events.subscriptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class StorageAccount {

    private UploadService upload;

    @GetMapping("/upload/imagine")
    public ResponseEntity<String>  uploadImagine(@RequestParam MultipartFile data){
        String url = this.upload.uploadBlob(data);
        return ResponseEntity.ok(url);
    }

}

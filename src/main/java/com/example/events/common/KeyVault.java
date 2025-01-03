package com.example.events.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.azure.core.exception.ClientAuthenticationException;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

@Component
public class KeyVault {

    protected static Logger logger = LoggerFactory.getLogger(KeyVault.class);

    SecretClient secretClient = new SecretClientBuilder()
    .vaultUrl("https://keyvaultaz204service.vault.azure.net/")
    .credential(new DefaultAzureCredentialBuilder().build())
    .buildClient();


    public String getSecret(String key){
         try {
            logger.info("[KEY VAULT] get key : " + key );
            KeyVaultSecret secret = secretClient.getSecret(key);
            return secret.getValue();
        } catch (ClientAuthenticationException e) {
            logger.error("[Key Vault ERROR] "  + e.getMessage());
            return "";
        }
    }

}

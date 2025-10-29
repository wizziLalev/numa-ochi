package com.numaochi.search;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Meilisearch client.
 */
@Configuration
public class MeilisearchConfig {

    @Value("${meilisearch.host}")
    private String meilisearchHost;

    @Value("${meilisearch.api-key}")
    private String meilisearchApiKey;

    /**
     * Creates and configures a Meilisearch client bean.
     *
     * @return a configured {@link Client} instance.
     */
    @Bean
    public Client meilisearchClient() {
        return new Client(new Config(meilisearchHost, meilisearchApiKey));
    }
}

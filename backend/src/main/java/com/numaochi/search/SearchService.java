package com.numaochi.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.model.SearchResult;
import com.numaochi.series.Series;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service for interacting with Meilisearch to index and search for Series.
 */
@Service
public class SearchService {

    private final Client meilisearchClient;
    private final ObjectMapper objectMapper;

    public SearchService(Client meilisearchClient, ObjectMapper objectMapper) {
        this.meilisearchClient = meilisearchClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Indexes a Series object in Meilisearch.
     *
     * @param series the Series object to index.
     */
    public void indexSeries(Series series) {
        try {
            Index index = meilisearchClient.index("series");
            index.addDocuments(objectMapper.writeValueAsString(List.of(series)));
        } catch (Exception e) {
            // TODO: Handle exception properly (e.g., log it, throw custom exception)
            System.err.println("Error indexing series: " + e.getMessage());
        }
    }

    /**
     * Removes a Series from the Meilisearch index.
     *
     * @param seriesId the ID of the Series to remove.
     */
    public void removeSeries(Long seriesId) {
        try {
            Index index = meilisearchClient.index("series");
            index.deleteDocument(String.valueOf(seriesId));
        } catch (Exception e) {
            // TODO: Handle exception properly (e.g., log it, throw custom exception)
            System.err.println("Error removing series from index: " + e.getMessage());
        }
    }

    /**
     * Searches for Series in Meilisearch based on a query string.
     *
     * @param query the search query.
     * @return a list of Series objects matching the query.
     */
    public List<Series> searchSeries(String query) {
        try {
            Index index = meilisearchClient.index("series");
            SearchResult result = index.search(query);
            return Arrays.asList(objectMapper.convertValue(result.getHits(), Series[].class));
        } catch (Exception e) {
            // TODO: Handle exception properly (e.g., log it, throw custom exception)
            System.err.println("Error searching series: " + e.getMessage());
            return List.of();
        }
    }
}

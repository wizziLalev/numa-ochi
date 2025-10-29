package com.numaochi.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.json.JacksonJsonProcessor;
import com.meilisearch.sdk.model.SearchResult;
import com.numaochi.series.Series;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private Client meilisearchClient;

    @Spy // Use @Spy for ObjectMapper to allow calling real methods while still mocking if needed
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private Index mockIndex;

    @InjectMocks
    private SearchService searchService;

    private Series series1;

    @BeforeEach
    void setUp() {
        series1 = new Series();
        series1.setId(1L);
        series1.setTitle("Test Series");
        series1.setAuthor("Test Author");
        series1.setPublicationDate(LocalDate.of(2023, 1, 1));
        series1.setDescription("Test Description");
        series1.setCoverImage("test.jpg");
        series1.setPublisher("Test Publisher");
        series1.setIsbn("1234567890");

        when(meilisearchClient.index(anyString())).thenReturn(mockIndex);
    }

    @Test
    void indexSeries_shouldCallAddDocuments() throws Exception {
        searchService.indexSeries(series1);

        verify(mockIndex, times(1)).addDocuments(anyString());
    }

    @Test
    void indexSeries_shouldHandleException() throws Exception {
        doThrow(new RuntimeException("Meilisearch error")).when(mockIndex).addDocuments(anyString());

        // No exception should be thrown from the service method, but the error should be logged (or handled)
        assertDoesNotThrow(() -> searchService.indexSeries(series1));
    }

    @Test
    void removeSeries_shouldCallDeleteDocument() throws Exception {
        searchService.removeSeries(1L);

        verify(mockIndex, times(1)).deleteDocument(String.valueOf(1L));
    }

    @Test
    void removeSeries_shouldHandleException() throws Exception {
        doThrow(new RuntimeException("Meilisearch error")).when(mockIndex).deleteDocument(anyString());

        assertDoesNotThrow(() -> searchService.removeSeries(1L));
    }

    @Test
    void searchSeries_shouldReturnListOfSeries() throws Exception {
        SearchResult mockSearchResult = new SearchResult();
        // Meilisearch SDK uses Jackson for internal conversions, so we need to simulate that
        JacksonJsonProcessor jsonProcessor = new JacksonJsonProcessor();
        String seriesJson = jsonProcessor.serialize(Collections.singletonList(series1));
        mockSearchResult.setHits(jsonProcessor.decode(seriesJson, List.class));

        when(mockIndex.search(anyString())).thenReturn(mockSearchResult);

        List<Series> result = searchService.searchSeries("query");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(series1.getTitle(), result.get(0).getTitle());
        verify(mockIndex, times(1)).search("query");
    }

    @Test
    void searchSeries_shouldReturnEmptyList_whenNoResults() throws Exception {
        SearchResult mockSearchResult = new SearchResult();
        mockSearchResult.setHits(Collections.emptyList());

        when(mockIndex.search(anyString())).thenReturn(mockSearchResult);

        List<Series> result = searchService.searchSeries("no results");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockIndex, times(1)).search("no results");
    }

    @Test
    void searchSeries_shouldHandleException() throws Exception {
        when(mockIndex.search(anyString())).thenThrow(new RuntimeException("Meilisearch error"));

        List<Series> result = searchService.searchSeries("query");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

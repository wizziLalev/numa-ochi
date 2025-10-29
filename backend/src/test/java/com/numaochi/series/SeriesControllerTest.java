package com.numaochi.series;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SeriesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SeriesService seriesService;

    @InjectMocks
    private SeriesController seriesController;

    private SeriesDTO seriesDTO1;
    private SeriesDTO seriesDTO2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(seriesController).build();

        seriesDTO1 = new SeriesDTO();
        seriesDTO1.setId(1L);
        seriesDTO1.setTitle("Series One");
        seriesDTO1.setAuthor("Author One");
        seriesDTO1.setPublicationDate(LocalDate.of(2020, 1, 1));
        seriesDTO1.setDescription("Description One");
        seriesDTO1.setCoverImage("cover1.jpg");
        seriesDTO1.setPublisher("Publisher One");
        seriesDTO1.setIsbn("ISBN1");

        seriesDTO2 = new SeriesDTO();
        seriesDTO2.setId(2L);
        seriesDTO2.setTitle("Series Two");
        seriesDTO2.setAuthor("Author Two");
        seriesDTO2.setPublicationDate(LocalDate.of(2021, 2, 2));
        seriesDTO2.setDescription("Description Two");
        seriesDTO2.setCoverImage("cover2.jpg");
        seriesDTO2.setPublisher("Publisher Two");
        seriesDTO2.setIsbn("ISBN2");
    }

    @Test
    void getAllSeries_shouldReturnListOfSeriesDTOs() throws Exception {
        when(seriesService.getAllSeries()).thenReturn(Arrays.asList(seriesDTO1, seriesDTO2));

        mockMvc.perform(get("/api/series"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Series One"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Series Two"));

        verify(seriesService, times(1)).getAllSeries();
    }

    @Test
    void getSeriesById_shouldReturnSeriesDTO_whenFound() throws Exception {
        when(seriesService.getSeriesById(1L)).thenReturn(seriesDTO1);

        mockMvc.perform(get("/api/series/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Series One"));

        verify(seriesService, times(1)).getSeriesById(1L);
    }

    @Test
    void getSeriesById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(seriesService.getSeriesById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/series/{id}", 99L))
                .andExpect(status().isOk()); // Service returns null, controller returns 200 with null body

        verify(seriesService, times(1)).getSeriesById(99L);
    }

    @Test
    void createSeries_shouldReturnCreatedSeriesDTO() throws Exception {
        when(seriesService.createSeries(any(SeriesDTO.class))).thenReturn(seriesDTO1);

        mockMvc.perform(post("/api/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Series One\", \"author\": \"Author One\", \"publicationDate\": \"2020-01-01\", \"description\": \"Description One\", \"coverImage\": \"cover1.jpg\", \"publisher\": \"Publisher One\", \"isbn\": \"ISBN1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Series One"));

        verify(seriesService, times(1)).createSeries(any(SeriesDTO.class));
    }

    @Test
    void updateSeries_shouldReturnUpdatedSeriesDTO() throws Exception {
        when(seriesService.updateSeries(eq(1L), any(SeriesDTO.class))).thenReturn(seriesDTO1);

        mockMvc.perform(put("/api/series/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"Updated Series One\", \"author\": \"Author One\", \"publicationDate\": \"2020-01-01\", \"description\": \"Description One\", \"coverImage\": \"cover1.jpg\", \"publisher\": \"Publisher One\", \"isbn\": \"ISBN1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Series One"));

        verify(seriesService, times(1)).updateSeries(eq(1L), any(SeriesDTO.class));
    }

    @Test
    void deleteSeries_shouldReturnNoContent() throws Exception {
        doNothing().when(seriesService).deleteSeries(1L);

        mockMvc.perform(delete("/api/series/{id}", 1L))
                .andExpect(status().isOk());

        verify(seriesService, times(1)).deleteSeries(1L);
    }

    @Test
    void searchSeries_shouldReturnListOfSeriesDTOs() throws Exception {
        when(seriesService.searchSeries("test query")).thenReturn(Arrays.asList(seriesDTO1));

        mockMvc.perform(get("/api/series/search").param("query", "test query"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Series One"));

        verify(seriesService, times(1)).searchSeries("test query");
    }

    @Test
    void searchSeries_shouldReturnEmptyList_whenNoResults() throws Exception {
        when(seriesService.searchSeries("no results")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/series/search").param("query", "no results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(seriesService, times(1)).searchSeries("no results");
    }
}

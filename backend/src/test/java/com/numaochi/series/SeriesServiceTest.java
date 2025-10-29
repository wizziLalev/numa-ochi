package com.numaochi.series;

import com.numaochi.search.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeriesServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SeriesService seriesService;

    private Series series1;
    private Series series2;
    private SeriesDTO seriesDTO1;
    private SeriesDTO seriesDTO2;

    @BeforeEach
    void setUp() {
        series1 = new Series();
        series1.setId(1L);
        series1.setTitle("Series One");
        series1.setAuthor("Author One");
        series1.setPublicationDate(LocalDate.of(2020, 1, 1));
        series1.setDescription("Description One");
        series1.setCoverImage("cover1.jpg");
        series1.setPublisher("Publisher One");
        series1.setIsbn("ISBN1");

        series2 = new Series();
        series2.setId(2L);
        series2.setTitle("Series Two");
        series2.setAuthor("Author Two");
        series2.setPublicationDate(LocalDate.of(2021, 2, 2));
        series2.setDescription("Description Two");
        series2.setCoverImage("cover2.jpg");
        series2.setPublisher("Publisher Two");
        series2.setIsbn("ISBN2");

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
    void getAllSeries_shouldReturnListOfSeriesDTOs() {
        when(seriesRepository.findAll()).thenReturn(Arrays.asList(series1, series2));

        List<SeriesDTO> result = seriesService.getAllSeries();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(seriesDTO1.getTitle(), result.get(0).getTitle());
        assertEquals(seriesDTO2.getTitle(), result.get(1).getTitle());
    }

    @Test
    void getSeriesById_shouldReturnSeriesDTO_whenFound() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series1));

        SeriesDTO result = seriesService.getSeriesById(1L);

        assertNotNull(result);
        assertEquals(seriesDTO1.getTitle(), result.getTitle());
    }

    @Test
    void getSeriesById_shouldReturnNull_whenNotFound() {
        when(seriesRepository.findById(3L)).thenReturn(Optional.empty());

        SeriesDTO result = seriesService.getSeriesById(3L);

        assertNull(result);
    }

    @Test
    void createSeries_shouldReturnCreatedSeriesDTO_andIndexSeries() {
        when(seriesRepository.save(any(Series.class))).thenReturn(series1);
        doNothing().when(searchService).indexSeries(any(Series.class));

        SeriesDTO result = seriesService.createSeries(seriesDTO1);

        assertNotNull(result);
        assertEquals(seriesDTO1.getTitle(), result.getTitle());
        verify(seriesRepository, times(1)).save(any(Series.class));
        verify(searchService, times(1)).indexSeries(any(Series.class));
    }

    @Test
    void updateSeries_shouldReturnUpdatedSeriesDTO_andIndexSeries() {
        when(seriesRepository.save(any(Series.class))).thenReturn(series1);
        doNothing().when(searchService).indexSeries(any(Series.class));

        SeriesDTO result = seriesService.updateSeries(1L, seriesDTO1);

        assertNotNull(result);
        assertEquals(seriesDTO1.getTitle(), result.getTitle());
        verify(seriesRepository, times(1)).save(any(Series.class));
        verify(searchService, times(1)).indexSeries(any(Series.class));
    }

    @Test
    void deleteSeries_shouldCallRepositoryDeleteById_andRemoveSeriesFromIndex() {
        doNothing().when(seriesRepository).deleteById(1L);
        doNothing().when(searchService).removeSeries(1L);

        seriesService.deleteSeries(1L);

        verify(seriesRepository, times(1)).deleteById(1L);
        verify(searchService, times(1)).removeSeries(1L);
    }

    @Test
    void searchSeries_shouldReturnListOfSeriesDTOs() {
        when(searchService.searchSeries("query")).thenReturn(Arrays.asList(series1));

        List<SeriesDTO> result = seriesService.searchSeries("query");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(seriesDTO1.getTitle(), result.get(0).getTitle());
        verify(searchService, times(1)).searchSeries("query");
    }

    @Test
    void searchSeries_shouldReturnEmptyList_whenNoResults() {
        when(searchService.searchSeries("no_results")).thenReturn(Collections.emptyList());

        List<SeriesDTO> result = seriesService.searchSeries("no_results");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(searchService, times(1)).searchSeries("no_results");
    }
}

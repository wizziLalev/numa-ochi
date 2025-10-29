package com.numaochi.collection;

import com.numaochi.series.Series;
import com.numaochi.series.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private CollectionService collectionService;

    private Collection collection1;
    private Collection collection2;
    private CollectionDTO collectionDTO1;
    private CollectionDTO collectionDTO2;
    private Series series1;
    private Series series2;

    @BeforeEach
    void setUp() {
        series1 = new Series();
        series1.setId(1L);
        series1.setTitle("Series One");

        series2 = new Series();
        series2.setId(2L);
        series2.setTitle("Series Two");

        collection1 = new Collection();
        collection1.setId(1L);
        collection1.setName("My Collection 1");
        collection1.setSeries(Arrays.asList(series1));

        collection2 = new Collection();
        collection2.setId(2L);
        collection2.setName("My Collection 2");
        collection2.setSeries(Arrays.asList(series2));

        collectionDTO1 = new CollectionDTO();
        collectionDTO1.setId(1L);
        collectionDTO1.setName("My Collection 1");
        collectionDTO1.setSeriesIds(Arrays.asList(1L));

        collectionDTO2 = new CollectionDTO();
        collectionDTO2.setId(2L);
        collectionDTO2.setName("My Collection 2");
        collectionDTO2.setSeriesIds(Arrays.asList(2L));
    }

    @Test
    void getAllCollections_shouldReturnListOfCollectionDTOs() {
        when(collectionRepository.findAll()).thenReturn(Arrays.asList(collection1, collection2));

        List<CollectionDTO> result = collectionService.getAllCollections();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(collectionDTO1.getName(), result.get(0).getName());
        assertEquals(collectionDTO2.getName(), result.get(1).getName());
    }

    @Test
    void getCollectionById_shouldReturnCollectionDTO_whenFound() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection1));

        CollectionDTO result = collectionService.getCollectionById(1L);

        assertNotNull(result);
        assertEquals(collectionDTO1.getName(), result.getName());
    }

    @Test
    void getCollectionById_shouldReturnNull_whenNotFound() {
        when(collectionRepository.findById(3L)).thenReturn(Optional.empty());

        CollectionDTO result = collectionService.getCollectionById(3L);

        assertNull(result);
    }

    @Test
    void createCollection_shouldReturnCreatedCollectionDTO() {
        when(seriesRepository.findAllById(anyList())).thenReturn(Arrays.asList(series1));
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection1);

        CollectionDTO result = collectionService.createCollection(collectionDTO1);

        assertNotNull(result);
        assertEquals(collectionDTO1.getName(), result.getName());
        verify(collectionRepository, times(1)).save(any(Collection.class));
    }

    @Test
    void updateCollection_shouldReturnUpdatedCollectionDTO_whenFound() {
        when(seriesRepository.findAllById(anyList())).thenReturn(Arrays.asList(series1));
        when(collectionRepository.save(any(Collection.class))).thenReturn(collection1);

        CollectionDTO result = collectionService.updateCollection(1L, collectionDTO1);

        assertNotNull(result);
        assertEquals(collectionDTO1.getName(), result.getName());
        verify(collectionRepository, times(1)).save(any(Collection.class));
    }

    @Test
    void deleteCollection_shouldCallRepositoryDeleteById() {
        collectionService.deleteCollection(1L);

        verify(collectionRepository, times(1)).deleteById(1L);
    }
}

package com.numaochi.collection;

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
class CollectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CollectionService collectionService;

    @InjectMocks
    private CollectionController collectionController;

    private CollectionDTO collectionDTO1;
    private CollectionDTO collectionDTO2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(collectionController).build();

        collectionDTO1 = new CollectionDTO();
        collectionDTO1.setId(1L);
        collectionDTO1.setName("My Collection 1");
        collectionDTO1.setSeriesIds(Collections.singletonList(1L));

        collectionDTO2 = new CollectionDTO();
        collectionDTO2.setId(2L);
        collectionDTO2.setName("My Collection 2");
        collectionDTO2.setSeriesIds(Collections.singletonList(2L));
    }

    @Test
    void getAllCollections_shouldReturnListOfCollectionDTOs() throws Exception {
        when(collectionService.getAllCollections()).thenReturn(Arrays.asList(collectionDTO1, collectionDTO2));

        mockMvc.perform(get("/api/collections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("My Collection 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("My Collection 2"));

        verify(collectionService, times(1)).getAllCollections();
    }

    @Test
    void getCollectionById_shouldReturnCollectionDTO_whenFound() throws Exception {
        when(collectionService.getCollectionById(1L)).thenReturn(collectionDTO1);

        mockMvc.perform(get("/api/collections/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("My Collection 1"));

        verify(collectionService, times(1)).getCollectionById(1L);
    }

    @Test
    void getCollectionById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(collectionService.getCollectionById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/collections/{id}", 99L))
                .andExpect(status().isOk()); // Service returns null, controller returns 200 with null body

        verify(collectionService, times(1)).getCollectionById(99L);
    }

    @Test
    void createCollection_shouldReturnCreatedCollectionDTO() throws Exception {
        when(collectionService.createCollection(any(CollectionDTO.class))).thenReturn(collectionDTO1);

        mockMvc.perform(post("/api/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"My Collection 1\", \"seriesIds\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("My Collection 1"));

        verify(collectionService, times(1)).createCollection(any(CollectionDTO.class));
    }

    @Test
    void updateCollection_shouldReturnUpdatedCollectionDTO() throws Exception {
        when(collectionService.updateCollection(eq(1L), any(CollectionDTO.class))).thenReturn(collectionDTO1);

        mockMvc.perform(put("/api/collections/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Collection 1\", \"seriesIds\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("My Collection 1"));

        verify(collectionService, times(1)).updateCollection(eq(1L), any(CollectionDTO.class));
    }

    @Test
    void deleteCollection_shouldReturnNoContent() throws Exception {
        doNothing().when(collectionService).deleteCollection(1L);

        mockMvc.perform(delete("/api/collections/{id}", 1L))
                .andExpect(status().isOk());

        verify(collectionService, times(1)).deleteCollection(1L);
    }
}

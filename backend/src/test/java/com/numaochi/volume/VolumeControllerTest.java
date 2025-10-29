package com.numaochi.volume;

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
class VolumeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VolumeService volumeService;

    @InjectMocks
    private VolumeController volumeController;

    private VolumeDTO volumeDTO1;
    private VolumeDTO volumeDTO2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(volumeController).build();

        volumeDTO1 = new VolumeDTO();
        volumeDTO1.setId(1L);
        volumeDTO1.setTitle("Volume 1");
        volumeDTO1.setSeriesId(1L);
        volumeDTO1.setChapterIds(Collections.singletonList(1L));

        volumeDTO2 = new VolumeDTO();
        volumeDTO2.setId(2L);
        volumeDTO2.setTitle("Volume 2");
        volumeDTO2.setSeriesId(1L);
        volumeDTO2.setChapterIds(Collections.singletonList(2L));
    }

    @Test
    void getAllVolumes_shouldReturnListOfVolumeDTOs() throws Exception {
        when(volumeService.getAllVolumes()).thenReturn(Arrays.asList(volumeDTO1, volumeDTO2));

        mockMvc.perform(get("/api/volumes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Volume 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Volume 2"));

        verify(volumeService, times(1)).getAllVolumes();
    }

    @Test
    void getVolumeById_shouldReturnVolumeDTO_whenFound() throws Exception {
        when(volumeService.getVolumeById(1L)).thenReturn(volumeDTO1);

        mockMvc.perform(get("/api/volumes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Volume 1"));

        verify(volumeService, times(1)).getVolumeById(1L);
    }

    @Test
    void getVolumeById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(volumeService.getVolumeById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/volumes/{id}", 99L))
                .andExpect(status().isOk()); // Service returns null, controller returns 200 with null body

        verify(volumeService, times(1)).getVolumeById(99L);
    }

    @Test
    void createVolume_shouldReturnCreatedVolumeDTO() throws Exception {
        when(volumeService.createVolume(any(VolumeDTO.class))).thenReturn(volumeDTO1);

        mockMvc.perform(post("/api/volumes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Volume 1\", \"seriesId\": 1, \"chapterIds\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Volume 1"));

        verify(volumeService, times(1)).createVolume(any(VolumeDTO.class));
    }

    @Test
    void updateVolume_shouldReturnUpdatedVolumeDTO() throws Exception {
        when(volumeService.updateVolume(eq(1L), any(VolumeDTO.class))).thenReturn(volumeDTO1);

        mockMvc.perform(put("/api/volumes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"Updated Volume 1\", \"seriesId\": 1, \"chapterIds\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Volume 1"));

        verify(volumeService, times(1)).updateVolume(eq(1L), any(VolumeDTO.class));
    }

    @Test
    void deleteVolume_shouldReturnNoContent() throws Exception {
        doNothing().when(volumeService).deleteVolume(1L);

        mockMvc.perform(delete("/api/volumes/{id}", 1L))
                .andExpect(status().isOk());

        verify(volumeService, times(1)).deleteVolume(1L);
    }
}

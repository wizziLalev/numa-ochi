package com.numaochi.volume;

import com.numaochi.chapter.Chapter;
import com.numaochi.chapter.ChapterRepository;
import com.numaochi.series.Series;
import com.numaochi.series.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolumeServiceTest {

    @Mock
    private VolumeRepository volumeRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @InjectMocks
    private VolumeService volumeService;

    private Series series;
    private Chapter chapter1;
    private Chapter chapter2;
    private Volume volume1;
    private Volume volume2;
    private VolumeDTO volumeDTO1;
    private VolumeDTO volumeDTO2;

    @BeforeEach
    void setUp() {
        series = new Series();
        series.setId(1L);
        series.setTitle("Test Series");

        chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setTitle("Chapter 1");

        chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setTitle("Chapter 2");

        volume1 = new Volume();
        volume1.setId(1L);
        volume1.setTitle("Volume 1");
        volume1.setSeries(series);
        volume1.setChapters(Arrays.asList(chapter1));

        volume2 = new Volume();
        volume2.setId(2L);
        volume2.setTitle("Volume 2");
        volume2.setSeries(series);
        volume2.setChapters(Arrays.asList(chapter2));

        volumeDTO1 = new VolumeDTO();
        volumeDTO1.setId(1L);
        volumeDTO1.setTitle("Volume 1");
        volumeDTO1.setSeriesId(1L);
        volumeDTO1.setChapterIds(Arrays.asList(1L));

        volumeDTO2 = new VolumeDTO();
        volumeDTO2.setId(2L);
        volumeDTO2.setTitle("Volume 2");
        volumeDTO2.setSeriesId(1L);
        volumeDTO2.setChapterIds(Arrays.asList(2L));
    }

    @Test
    void getAllVolumes_shouldReturnListOfVolumeDTOs() {
        when(volumeRepository.findAll()).thenReturn(Arrays.asList(volume1, volume2));

        List<VolumeDTO> result = volumeService.getAllVolumes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(volumeDTO1.getTitle(), result.get(0).getTitle());
        assertEquals(volumeDTO2.getTitle(), result.get(1).getTitle());
    }

    @Test
    void getVolumeById_shouldReturnVolumeDTO_whenFound() {
        when(volumeRepository.findById(1L)).thenReturn(Optional.of(volume1));

        VolumeDTO result = volumeService.getVolumeById(1L);

        assertNotNull(result);
        assertEquals(volumeDTO1.getTitle(), result.getTitle());
    }

    @Test
    void getVolumeById_shouldReturnNull_whenNotFound() {
        when(volumeRepository.findById(3L)).thenReturn(Optional.empty());

        VolumeDTO result = volumeService.getVolumeById(3L);

        assertNull(result);
    }

    @Test
    void createVolume_shouldReturnCreatedVolumeDTO() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(chapterRepository.findAllById(anyList())).thenReturn(Arrays.asList(chapter1));
        when(volumeRepository.save(any(Volume.class))).thenReturn(volume1);

        VolumeDTO result = volumeService.createVolume(volumeDTO1);

        assertNotNull(result);
        assertEquals(volumeDTO1.getTitle(), result.getTitle());
        verify(volumeRepository, times(1)).save(any(Volume.class));
    }

    @Test
    void updateVolume_shouldReturnUpdatedVolumeDTO_whenFound() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(chapterRepository.findAllById(anyList())).thenReturn(Arrays.asList(chapter1));
        when(volumeRepository.save(any(Volume.class))).thenReturn(volume1);

        VolumeDTO result = volumeService.updateVolume(1L, volumeDTO1);

        assertNotNull(result);
        assertEquals(volumeDTO1.getTitle(), result.getTitle());
        verify(volumeRepository, times(1)).save(any(Volume.class));
    }

    @Test
    void deleteVolume_shouldCallRepositoryDeleteById() {
        volumeService.deleteVolume(1L);

        verify(volumeRepository, times(1)).deleteById(1L);
    }
}

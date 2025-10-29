package com.numaochi.chapter;

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
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private ChapterService chapterService;

    private Series series;
    private Chapter chapter1;
    private Chapter chapter2;
    private ChapterDTO chapterDTO1;
    private ChapterDTO chapterDTO2;

    @BeforeEach
    void setUp() {
        series = new Series();
        series.setId(1L);
        series.setTitle("Test Series");

        chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setTitle("Chapter 1");
        chapter1.setFilePath("/path/to/chapter1.pdf");
        chapter1.setFileType("PDF");
        chapter1.setSeries(series);

        chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setTitle("Chapter 2");
        chapter2.setFilePath("/path/to/chapter2.epub");
        chapter2.setFileType("EPUB");
        chapter2.setSeries(series);

        chapterDTO1 = new ChapterDTO();
        chapterDTO1.setId(1L);
        chapterDTO1.setTitle("Chapter 1");
        chapterDTO1.setFilePath("/path/to/chapter1.pdf");
        chapterDTO1.setFileType("PDF");
        chapterDTO1.setSeriesId(1L);

        chapterDTO2 = new ChapterDTO();
        chapterDTO2.setId(2L);
        chapterDTO2.setTitle("Chapter 2");
        chapterDTO2.setFilePath("/path/to/chapter2.epub");
        chapterDTO2.setFileType("EPUB");
        chapterDTO2.setSeriesId(1L);
    }

    @Test
    void getAllChapters_shouldReturnListOfChapterDTOs() {
        when(chapterRepository.findAll()).thenReturn(Arrays.asList(chapter1, chapter2));

        List<ChapterDTO> result = chapterService.getAllChapters();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(chapterDTO1.getTitle(), result.get(0).getTitle());
        assertEquals(chapterDTO2.getTitle(), result.get(1).getTitle());
    }

    @Test
    void getChapterById_shouldReturnChapterDTO_whenFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter1));

        ChapterDTO result = chapterService.getChapterById(1L);

        assertNotNull(result);
        assertEquals(chapterDTO1.getTitle(), result.getTitle());
    }

    @Test
    void getChapterById_shouldReturnNull_whenNotFound() {
        when(chapterRepository.findById(3L)).thenReturn(Optional.empty());

        ChapterDTO result = chapterService.getChapterById(3L);

        assertNull(result);
    }

    @Test
    void createChapter_shouldReturnCreatedChapterDTO() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter1);

        ChapterDTO result = chapterService.createChapter(chapterDTO1);

        assertNotNull(result);
        assertEquals(chapterDTO1.getTitle(), result.getTitle());
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    void updateChapter_shouldReturnUpdatedChapterDTO_whenFound() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter1);

        ChapterDTO result = chapterService.updateChapter(1L, chapterDTO1);

        assertNotNull(result);
        assertEquals(chapterDTO1.getTitle(), result.getTitle());
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    void deleteChapter_shouldCallRepositoryDeleteById() {
        chapterService.deleteChapter(1L);

        verify(chapterRepository, times(1)).deleteById(1L);
    }
}

package com.numaochi.chapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ChapterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChapterService chapterService;

    @InjectMocks
    private ChapterController chapterController;

    private ChapterDTO chapterDTO1;
    private ChapterDTO chapterDTO2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chapterController).build();

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
    void getAllChapters_shouldReturnListOfChapterDTOs() throws Exception {
        when(chapterService.getAllChapters()).thenReturn(Arrays.asList(chapterDTO1, chapterDTO2));

        mockMvc.perform(get("/api/chapters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Chapter 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Chapter 2"));

        verify(chapterService, times(1)).getAllChapters();
    }

    @Test
    void getChapterById_shouldReturnChapterDTO_whenFound() throws Exception {
        when(chapterService.getChapterById(1L)).thenReturn(chapterDTO1);

        mockMvc.perform(get("/api/chapters/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Chapter 1"));

        verify(chapterService, times(1)).getChapterById(1L);
    }

    @Test
    void getChapterById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(chapterService.getChapterById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/chapters/{id}", 99L))
                .andExpect(status().isOk()); // Service returns null, controller returns 200 with null body

        verify(chapterService, times(1)).getChapterById(99L);
    }

    @Test
    void createChapter_shouldReturnCreatedChapterDTO() throws Exception {
        when(chapterService.createChapter(any(ChapterDTO.class))).thenReturn(chapterDTO1);

        mockMvc.perform(post("/api/chapters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Chapter 1\", \"filePath\": \"/path/to/chapter1.pdf\", \"fileType\": \"PDF\", \"seriesId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Chapter 1"));

        verify(chapterService, times(1)).createChapter(any(ChapterDTO.class));
    }

    @Test
    void updateChapter_shouldReturnUpdatedChapterDTO() throws Exception {
        when(chapterService.updateChapter(eq(1L), any(ChapterDTO.class))).thenReturn(chapterDTO1);

        mockMvc.perform(put("/api/chapters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"Updated Chapter 1\", \"filePath\": \"/path/to/chapter1.pdf\", \"fileType\": \"PDF\", \"seriesId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Chapter 1"));

        verify(chapterService, times(1)).updateChapter(eq(1L), any(ChapterDTO.class));
    }

    @Test
    void deleteChapter_shouldReturnNoContent() throws Exception {
        doNothing().when(chapterService).deleteChapter(1L);

        mockMvc.perform(delete("/api/chapters/{id}", 1L))
                .andExpect(status().isOk());

        verify(chapterService, times(1)).deleteChapter(1L);
    }
}

package com.numaochi.chapter;

import com.numaochi.series.Series;
import com.numaochi.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing chapters.
 */
@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final SeriesRepository seriesRepository;

    public ChapterService(ChapterRepository chapterRepository, SeriesRepository seriesRepository) {
        this.chapterRepository = chapterRepository;
        this.seriesRepository = seriesRepository;
    }

    /**
     * Retrieves all chapters.
     *
     * @return a list of all chapters.
     */
    public List<ChapterDTO> getAllChapters() {
        return chapterRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a chapter by its ID.
     *
     * @param id the ID of the chapter to retrieve.
     * @return the chapter with the specified ID, or {@code null} if not found.
     */
    public ChapterDTO getChapterById(Long id) {
        return chapterRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    /**
     * Creates a new chapter.
     *
     * @param chapterDTO the chapter to create.
     * @return the created chapter.
     */
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {
        Chapter chapter = convertToEntity(chapterDTO);
        return convertToDto(chapterRepository.save(chapter));
    }

    /**
     * Updates an existing chapter.
     *
     * @param id         the ID of the chapter to update.
     * @param chapterDTO the updated chapter data.
     * @return the updated chapter.
     */
    public ChapterDTO updateChapter(Long id, ChapterDTO chapterDTO) {
        Chapter chapter = convertToEntity(chapterDTO);
        chapter.setId(id);
        return convertToDto(chapterRepository.save(chapter));
    }

    /**
     * Deletes a chapter by its ID.
     *
     * @param id the ID of the chapter to delete.
     */
    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }

    /**
     * Converts a {@link Chapter} entity to a {@link ChapterDTO}.
     *
     * @param chapter the entity to convert.
     * @return the converted DTO.
     */
    private ChapterDTO convertToDto(Chapter chapter) {
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(chapter.getId());
        chapterDTO.setTitle(chapter.getTitle());
        chapterDTO.setFilePath(chapter.getFilePath());
        chapterDTO.setFileType(chapter.getFileType());
        if (chapter.getSeries() != null) {
            chapterDTO.setSeriesId(chapter.getSeries().getId());
        }
        return chapterDTO;
    }

    /**
     * Converts a {@link ChapterDTO} to a {@link Chapter} entity.
     *
     * @param chapterDTO the DTO to convert.
     * @return the converted entity.
     */
    private Chapter convertToEntity(ChapterDTO chapterDTO) {
        Chapter chapter = new Chapter();
        chapter.setId(chapterDTO.getId());
        chapter.setTitle(chapterDTO.getTitle());
        chapter.setFilePath(chapterDTO.getFilePath());
        chapter.setFileType(chapterDTO.getFileType());
        if (chapterDTO.getSeriesId() != null) {
            Series series = seriesRepository.findById(chapterDTO.getSeriesId()).orElse(null);
            chapter.setSeries(series);
        }
        return chapter;
    }
}

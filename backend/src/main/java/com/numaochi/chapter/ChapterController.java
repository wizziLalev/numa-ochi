package com.numaochi.chapter;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing chapters.
 */
@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    /**
     * Retrieves all chapters.
     *
     * @return a list of all chapters.
     */
    @GetMapping
    public List<ChapterDTO> getAllChapters() {
        return chapterService.getAllChapters();
    }

    /**
     * Retrieves a chapter by its ID.
     *
     * @param id the ID of the chapter to retrieve.
     * @return the chapter with the specified ID.
     */
    @GetMapping("/{id}")
    public ChapterDTO getChapterById(@PathVariable Long id) {
        return chapterService.getChapterById(id);
    }

    /**
     * Creates a new chapter.
     *
     * @param chapterDTO the chapter to create.
     * @return the created chapter.
     */
    @PostMapping
    public ChapterDTO createChapter(@RequestBody ChapterDTO chapterDTO) {
        return chapterService.createChapter(chapterDTO);
    }

    /**
     * Updates an existing chapter.
     *
     * @param id         the ID of the chapter to update.
     * @param chapterDTO the updated chapter data.
     * @return the updated chapter.
     */
    @PutMapping("/{id}")
    public ChapterDTO updateChapter(@PathVariable Long id, @RequestBody ChapterDTO chapterDTO) {
        return chapterService.updateChapter(id, chapterDTO);
    }

    /**
     * Deletes a chapter by its ID.
     *
     * @param id the ID of the chapter to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
    }
}

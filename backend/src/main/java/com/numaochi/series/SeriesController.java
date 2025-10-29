package com.numaochi.series;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing series.
 */
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    /**
     * Retrieves all series.
     *
     * @return a list of all series.
     */
    @GetMapping
    public List<SeriesDTO> getAllSeries() {
        return seriesService.getAllSeries();
    }

    /**
     * Retrieves a series by its ID.
     *
     * @param id the ID of the series to retrieve.
     * @return the series with the specified ID.
     */
    @GetMapping("/{id}")
    public SeriesDTO getSeriesById(@PathVariable Long id) {
        return seriesService.getSeriesById(id);
    }

    /**
     * Creates a new series.
     *
     * @param seriesDTO the series to create.
     * @return the created series.
     */
    @PostMapping
    public SeriesDTO createSeries(@RequestBody SeriesDTO seriesDTO) {
        return seriesService.createSeries(seriesDTO);
    }

    /**
     * Updates an existing series.
     *
     * @param id        the ID of the series to update.
     * @param seriesDTO the updated series data.
     * @return the updated series.
     */
    @PutMapping("/{id}")
    public SeriesDTO updateSeries(@PathVariable Long id, @RequestBody SeriesDTO seriesDTO) {
        return seriesService.updateSeries(id, seriesDTO);
    }

    /**
     * Deletes a series by its ID.
     *
     * @param id the ID of the series to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
    }

    /**
     * Searches for series based on a query string.
     *
     * @param query the search query.
     * @return a list of series matching the query.
     */
    @GetMapping("/search")
    public List<SeriesDTO> searchSeries(@RequestParam String query) {
        return seriesService.searchSeries(query);
    }
}

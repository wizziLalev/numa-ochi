package com.numaochi.series;

import com.numaochi.search.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing series.
 */
@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SearchService searchService;

    public SeriesService(SeriesRepository seriesRepository, SearchService searchService) {
        this.seriesRepository = seriesRepository;
        this.searchService = searchService;
    }

    /**
     * Retrieves all series.
     *
     * @return a list of all series.
     */
    public List<SeriesDTO> getAllSeries() {
        return seriesRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a series by its ID.
     *
     * @param id the ID of the series to retrieve.
     * @return the series with the specified ID, or {@code null} if not found.
     */
    public SeriesDTO getSeriesById(Long id) {
        return seriesRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    /**
     * Creates a new series and indexes it in Meilisearch.
     *
     * @param seriesDTO the series to create.
     * @return the created series.
     */
    public SeriesDTO createSeries(SeriesDTO seriesDTO) {
        Series series = convertToEntity(seriesDTO);
        Series savedSeries = seriesRepository.save(series);
        searchService.indexSeries(savedSeries);
        return convertToDto(savedSeries);
    }

    /**
     * Updates an existing series and re-indexes it in Meilisearch.
     *
     * @param id        the ID of the series to update.
     * @param seriesDTO the updated series data.
     * @return the updated series.
     */
    public SeriesDTO updateSeries(Long id, SeriesDTO seriesDTO) {
        Series series = convertToEntity(seriesDTO);
        series.setId(id);
        Series savedSeries = seriesRepository.save(series);
        searchService.indexSeries(savedSeries);
        return convertToDto(savedSeries);
    }

    /**
     * Deletes a series by its ID and removes it from Meilisearch.
     *
     * @param id the ID of the series to delete.
     */
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
        searchService.removeSeries(id);
    }

    /**
     * Searches for series using Meilisearch.
     *
     * @param query the search query string.
     * @return a list of series matching the query.
     */
    public List<SeriesDTO> searchSeries(String query) {
        return searchService.searchSeries(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link Series} entity to a {@link SeriesDTO}.
     *
     * @param series the entity to convert.
     * @return the converted DTO.
     */
    private SeriesDTO convertToDto(Series series) {
        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setId(series.getId());
        seriesDTO.setTitle(series.getTitle());
        seriesDTO.setAuthor(series.getAuthor());
        seriesDTO.setPublicationDate(series.getPublicationDate());
        seriesDTO.setDescription(series.getDescription());
        seriesDTO.setCoverImage(series.getCoverImage());
        seriesDTO.setPublisher(series.getPublisher());
        seriesDTO.setIsbn(series.getIsbn());
        return seriesDTO;
    }

    /**
     * Converts a {@link SeriesDTO} to a {@link Series} entity.
     *
     * @param seriesDTO the DTO to convert.
     * @return the converted entity.
     */
    private Series convertToEntity(SeriesDTO seriesDTO) {
        Series series = new Series();
        series.setId(seriesDTO.getId());
        series.setTitle(seriesDTO.getTitle());
        series.setAuthor(seriesDTO.getAuthor());
        series.setPublicationDate(seriesDTO.getPublicationDate());
        series.setDescription(seriesDTO.getDescription());
        series.setCoverImage(seriesDTO.getCoverImage());
        series.setPublisher(seriesDTO.getPublisher());
        series.setIsbn(seriesDTO.getIsbn());
        return series;
    }
}

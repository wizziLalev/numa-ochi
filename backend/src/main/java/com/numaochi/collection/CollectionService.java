package com.numaochi.collection;

import com.numaochi.series.Series;
import com.numaochi.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing collections.
 */
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final SeriesRepository seriesRepository;

    public CollectionService(CollectionRepository collectionRepository, SeriesRepository seriesRepository) {
        this.collectionRepository = collectionRepository;
        this.seriesRepository = seriesRepository;
    }

    /**
     * Retrieves all collections.
     *
     * @return a list of all collections.
     */
    public List<CollectionDTO> getAllCollections() {
        return collectionRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a collection by its ID.
     *
     * @param id the ID of the collection to retrieve.
     * @return the collection with the specified ID, or {@code null} if not found.
     */
    public CollectionDTO getCollectionById(Long id) {
        return collectionRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    /**
     * Creates a new collection.
     *
     * @param collectionDTO the collection to create.
     * @return the created collection.
     */
    public CollectionDTO createCollection(CollectionDTO collectionDTO) {
        Collection collection = convertToEntity(collectionDTO);
        return convertToDto(collectionRepository.save(collection));
    }

    /**
     * Updates an existing collection.
     *
     * @param id            the ID of the collection to update.
     * @param collectionDTO the updated collection data.
     * @return the updated collection.
     */
    public CollectionDTO updateCollection(Long id, CollectionDTO collectionDTO) {
        Collection collection = convertToEntity(collectionDTO);
        collection.setId(id);
        return convertToDto(collectionRepository.save(collection));
    }

    /**
     * Deletes a collection by its ID.
     *
     * @param id the ID of the collection to delete.
     */
    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    /**
     * Converts a {@link Collection} entity to a {@link CollectionDTO}.
     *
     * @param collection the entity to convert.
     * @return the converted DTO.
     */
    private CollectionDTO convertToDto(Collection collection) {
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setId(collection.getId());
        collectionDTO.setName(collection.getName());
        if (collection.getSeries() != null) {
            collectionDTO.setSeriesIds(collection.getSeries().stream().map(Series::getId).collect(Collectors.toList()));
        }
        return collectionDTO;
    }

    /**
     * Converts a {@link CollectionDTO} to a {@link Collection} entity.
     *
     * @param collectionDTO the DTO to convert.
     * @return the converted entity.
     */
    private Collection convertToEntity(CollectionDTO collectionDTO) {
        Collection collection = new Collection();
        collection.setId(collectionDTO.getId());
        collection.setName(collectionDTO.getName());
        if (collectionDTO.getSeriesIds() != null) {
            List<Series> series = seriesRepository.findAllById(collectionDTO.getSeriesIds());
            collection.setSeries(series);
        }
        return collection;
    }
}

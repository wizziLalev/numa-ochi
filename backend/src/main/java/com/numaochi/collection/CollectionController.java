package com.numaochi.collection;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing collections.
 */
@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * Retrieves all collections.
     *
     * @return a list of all collections.
     */
    @GetMapping
    public List<CollectionDTO> getAllCollections() {
        return collectionService.getAllCollections();
    }

    /**
     * Retrieves a collection by its ID.
     *
     * @param id the ID of the collection to retrieve.
     * @return the collection with the specified ID.
     */
    @GetMapping("/{id}")
    public CollectionDTO getCollectionById(@PathVariable Long id) {
        return collectionService.getCollectionById(id);
    }

    /**
     * Creates a new collection.
     *
     * @param collectionDTO the collection to create.
     * @return the created collection.
     */
    @PostMapping
    public CollectionDTO createCollection(@RequestBody CollectionDTO collectionDTO) {
        return collectionService.createCollection(collectionDTO);
    }

    /**
     * Updates an existing collection.
     *
     * @param id            the ID of the collection to update.
     * @param collectionDTO the updated collection data.
     * @return the updated collection.
     */
    @PutMapping("/{id}")
    public CollectionDTO updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
        return collectionService.updateCollection(id, collectionDTO);
    }

    /**
     * Deletes a collection by its ID.
     *
     * @param id the ID of the collection to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
    }
}

package com.numaochi.collection;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing {@link Collection} entities.
 */
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}

package com.numaochi.volume;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing {@link Volume} entities.
 */
public interface VolumeRepository extends JpaRepository<Volume, Long> {
}

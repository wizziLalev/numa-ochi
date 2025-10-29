package com.numaochi.series;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing {@link Series} entities.
 */
public interface SeriesRepository extends JpaRepository<Series, Long> {
}

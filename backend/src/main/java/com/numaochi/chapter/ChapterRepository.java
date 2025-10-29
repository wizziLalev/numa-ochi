package com.numaochi.chapter;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing {@link Chapter} entities.
 */
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}

package com.numaochi.collection;

import java.util.List;

/**
 * Data Transfer Object for Collection entities.
 */
public class CollectionDTO {

    private Long id;
    private String name;
    private List<Long> seriesIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getSeriesIds() {
        return seriesIds;
    }

    public void setSeriesIds(List<Long> seriesIds) {
        this.seriesIds = seriesIds;
    }
}

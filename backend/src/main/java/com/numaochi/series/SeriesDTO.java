package com.numaochi.series;

import java.time.LocalDate;

/**
 * Data Transfer Object for Series entities.
 */
public class SeriesDTO {

    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String description;
    private String coverImage;
    private String publisher;
    private String isbn;

    /**
     * Returns the unique identifier of the series.
     * @return the ID of the series.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the series.
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the title of the series.
     * @return the title of the series.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the series.
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author of the series.
     * @return the author of the series.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the series.
     * @param author the author to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the publication date of the series.
     * @return the publication date of the series.
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date of the series.
     * @param publicationDate the publication date to set.
     */
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Returns the description or summary of the series.
     * @return the description of the series.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or summary of the series.
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the main cover image URL or path of the series.
     * @return the cover image of the series.
     */
    public String getCoverImage() {
        return coverImage;
    }

    /**
     * Sets the main cover image URL or path of the series.
     * @param coverImage the cover image to set.
     */
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * Returns the publisher of the series.
     * @return the publisher of the series.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the series.
     * @param publisher the publisher to set.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the ISBN of the series.
     * @return the ISBN of the series.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the series.
     * @param isbn the ISBN to set.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

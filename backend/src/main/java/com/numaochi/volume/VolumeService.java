package com.numaochi.volume;

import com.numaochi.chapter.Chapter;
import com.numaochi.chapter.ChapterRepository;
import com.numaochi.series.Series;
import com.numaochi.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing volumes.
 */
@Service
public class VolumeService {

    private final VolumeRepository volumeRepository;
    private final SeriesRepository seriesRepository;
    private final ChapterRepository chapterRepository;

    public VolumeService(VolumeRepository volumeRepository, SeriesRepository seriesRepository, ChapterRepository chapterRepository) {
        this.volumeRepository = volumeRepository;
        this.seriesRepository = seriesRepository;
        this.chapterRepository = chapterRepository;
    }

    /**
     * Retrieves all volumes.
     *
     * @return a list of all volumes.
     */
    public List<VolumeDTO> getAllVolumes() {
        return volumeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a volume by its ID.
     *
     * @param id the ID of the volume to retrieve.
     * @return the volume with the specified ID, or {@code null} if not found.
     */
    public VolumeDTO getVolumeById(Long id) {
        return volumeRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    /**
     * Creates a new volume.
     *
     * @param volumeDTO the volume to create.
     * @return the created volume.
     */
    public VolumeDTO createVolume(VolumeDTO volumeDTO) {
        Volume volume = convertToEntity(volumeDTO);
        return convertToDto(volumeRepository.save(volume));
    }

    /**
     * Updates an existing volume.
     *
     * @param id        the ID of the volume to update.
     * @param volumeDTO the updated volume data.
     * @return the updated volume.
     */
    public VolumeDTO updateVolume(Long id, VolumeDTO volumeDTO) {
        Volume volume = convertToEntity(volumeDTO);
        volume.setId(id);
        return convertToDto(volumeRepository.save(volume));
    }

    /**
     * Deletes a volume by its ID.
     *
     * @param id the ID of the volume to delete.
     */
    public void deleteVolume(Long id) {
        volumeRepository.deleteById(id);
    }

    /**
     * Converts a {@link Volume} entity to a {@link VolumeDTO}.
     *
     * @param volume the entity to convert.
     * @return the converted DTO.
     */
    private VolumeDTO convertToDto(Volume volume) {
        VolumeDTO volumeDTO = new VolumeDTO();
        volumeDTO.setId(volume.getId());
        volumeDTO.setTitle(volume.getTitle());
        volumeDTO.setAuthor(volume.getAuthor());
        volumeDTO.setPublicationDate(volume.getPublicationDate());
        volumeDTO.setDescription(volume.getDescription());
        volumeDTO.setCoverImage(volume.getCoverImage());
        volumeDTO.setPublisher(volume.getPublisher());
        volumeDTO.setIsbn(volume.getIsbn());
        if (volume.getSeries() != null) {
            volumeDTO.setSeriesId(volume.getSeries().getId());
        }
        if (volume.getChapters() != null) {
            volumeDTO.setChapterIds(volume.getChapters().stream().map(Chapter::getId).collect(Collectors.toList()));
        }
        return volumeDTO;
    }

    /**
     * Converts a {@link VolumeDTO} to a {@link Volume} entity.
     *
     * @param volumeDTO the DTO to convert.
     * @return the converted entity.
     */
    private Volume convertToEntity(VolumeDTO volumeDTO) {
        Volume volume = new Volume();
        volume.setId(volumeDTO.getId());
        volume.setTitle(volumeDTO.getTitle());
        volume.setAuthor(volumeDTO.getAuthor());
        volume.setPublicationDate(volumeDTO.getPublicationDate());
        volume.setDescription(volumeDTO.getDescription());
        volume.setCoverImage(volumeDTO.getCoverImage());
        volume.setPublisher(volumeDTO.getPublisher());
        volume.setIsbn(volumeDTO.getIsbn());
        if (volumeDTO.getSeriesId() != null) {
            Series series = seriesRepository.findById(volumeDTO.getSeriesId()).orElse(null);
            volume.setSeries(series);
        }
        if (volumeDTO.getChapterIds() != null) {
            List<Chapter> chapters = chapterRepository.findAllById(volumeDTO.getChapterIds());
            volume.setChapters(chapters);
        }
        return volume;
    }
}

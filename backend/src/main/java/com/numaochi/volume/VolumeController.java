package com.numaochi.volume;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing volumes.
 */
@RestController
@RequestMapping("/api/volumes")
public class VolumeController {

    private final VolumeService volumeService;

    public VolumeController(VolumeService volumeService) {
        this.volumeService = volumeService;
    }

    /**
     * Retrieves all volumes.
     *
     * @return a list of all volumes.
     */
    @GetMapping
    public List<VolumeDTO> getAllVolumes() {
        return volumeService.getAllVolumes();
    }

    /**
     * Retrieves a volume by its ID.
     *
     * @param id the ID of the volume to retrieve.
     * @return the volume with the specified ID.
     */
    @GetMapping("/{id}")
    public VolumeDTO getVolumeById(@PathVariable Long id) {
        return volumeService.getVolumeById(id);
    }

    /**
     * Creates a new volume.
     *
     * @param volumeDTO the volume to create.
     * @return the created volume.
     */
    @PostMapping
    public VolumeDTO createVolume(@RequestBody VolumeDTO volumeDTO) {
        return volumeService.createVolume(volumeDTO);
    }

    /**
     * Updates an existing volume.
     *
     * @param id        the ID of the volume to update.
     * @param volumeDTO the updated volume data.
     * @return the updated volume.
     */
    @PutMapping("/{id}")
    public VolumeDTO updateVolume(@PathVariable Long id, @RequestBody VolumeDTO volumeDTO) {
        return volumeService.updateVolume(id, volumeDTO);
    }

    /**
     * Deletes a volume by its ID.
     *
     * @param id the ID of the volume to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteVolume(@PathVariable Long id) {
        volumeService.deleteVolume(id);
    }
}

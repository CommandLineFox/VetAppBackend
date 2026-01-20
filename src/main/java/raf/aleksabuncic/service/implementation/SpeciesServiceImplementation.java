package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.exception.UsedResourceException;
import raf.aleksabuncic.mapper.SpeciesMapper;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.SpeciesService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpeciesServiceImplementation implements SpeciesService {
    private final SpeciesMapper speciesMapper;
    private final SpeciesRepository speciesRepository;

    @Transactional(readOnly = true)
    @Override
    public SpeciesDto findSpeciesById(Long id) {
        log.info("Finding species by id: {}", id);

        Species species = speciesRepository.getSpeciesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        return speciesMapper.speciesToSpeciesDto(species);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SpeciesDto> findAllSpecies() {
        log.info("Finding all species");

        return speciesRepository.findAll()
                .stream()
                .map(speciesMapper::speciesToSpeciesDto)
                .toList();
    }

    @Override
    public SpeciesDto createSpecies(SpeciesCreateDto speciesCreateDto) {
        log.info("Creating species: {}", speciesCreateDto);

        Species species = speciesMapper.speciesCreateDtoToSpecies(speciesCreateDto);

        try {
            speciesRepository.save(species);
            return speciesMapper.speciesToSpeciesDto(species);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Species already exists for this name: " + speciesCreateDto.getName());
        }
    }

    @Override
    public SpeciesDto updateSpecies(Long id, SpeciesUpdateDto speciesUpdateDto) {
        log.info("Updating species: {}", speciesUpdateDto);

        Species species = speciesRepository.getSpeciesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        if (speciesUpdateDto.getName() != null) {
            species.setName(speciesUpdateDto.getName());
        }

        log.info("Species updated: {}", species);
        return speciesMapper.speciesToSpeciesDto(species);
    }

    @Override
    public void deleteSpecies(Long id) {
        log.info("Deleting species with id: {}", id);

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        try {
            speciesRepository.delete(species);
            log.info("Species deleted: {}", species);
        } catch (DataIntegrityViolationException e) {
            throw new UsedResourceException("Cannot delete species with id: " + id + " because it is associated with other resources");
        }
    }
}

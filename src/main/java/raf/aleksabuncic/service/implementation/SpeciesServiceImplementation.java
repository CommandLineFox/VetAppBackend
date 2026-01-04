package raf.aleksabuncic.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.dto.SpeciesDto;
import raf.aleksabuncic.dto.SpeciesRequestDto;
import raf.aleksabuncic.exception.DuplicateResourceException;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.mapper.SpeciesMapper;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.service.SpeciesService;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImplementation implements SpeciesService {
    private final SpeciesMapper speciesMapper;
    private final SpeciesRepository speciesRepository;

    @Override
    public SpeciesDto findSpeciesById(Long id) {
        Species species = speciesRepository.getSpeciesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        return speciesMapper.speciesToSpeciesDto(species);
    }

    @Override
    public SpeciesDto createSpecies(SpeciesRequestDto speciesRequestDto) {
        Species species = speciesMapper.speciesRequestDtoToSpecies(speciesRequestDto);

        try {
            speciesRepository.save(species);
            return speciesMapper.speciesToSpeciesDto(speciesRepository.save(species));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Species already exists for this name: " + speciesRequestDto.getName());
        }
    }

    @Override
    public SpeciesDto updateSpecies(Long id, SpeciesRequestDto speciesRequestDto) {
        Species species = speciesRepository.getSpeciesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found for this id: " + id));

        if (speciesRequestDto.getName() != null) {
            species.setName(speciesRequestDto.getName());
        }

        speciesRepository.save(species);
        return speciesMapper.speciesToSpeciesDto(species);
    }

    @Override
    public void deleteSpecies(Long id) {
        if (!speciesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Species not found for this id: " + id);
        }

        speciesRepository.deleteById(id);
    }
}

package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;

@Mapper(componentModel = "spring")
public interface VeterinarianMapper {
    VeterinarianDto veterinarianToVeterinarianDto(Veterinarian veterinarian);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    Veterinarian veterinarianCreateDtoToVeterinarian(VeterinarianCreateDto veterinarianCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void veterinarianUpdateDtoToVeterinarian(VeterinarianUpdateDto veterinarianUpdateDto, @MappingTarget Veterinarian veterinarian);
}

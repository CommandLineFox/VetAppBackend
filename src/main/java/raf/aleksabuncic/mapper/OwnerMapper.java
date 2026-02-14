package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    OwnerDto ownerToOwnerDto(Owner owner);

    @Mapping(target = "id", ignore = true)
    Owner ownerCreateDtoToOwner(OwnerCreateDto ownerCreateDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void ownerUpdateDtoToOwner(OwnerUpdateDto ownerUpdateDto, @MappingTarget Owner owner);
}

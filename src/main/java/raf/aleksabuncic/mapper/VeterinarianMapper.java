package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
import raf.aleksabuncic.security.Permission;
import raf.aleksabuncic.security.PermissionUtils;

import java.util.Set;

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

    default Set<Permission> mapLongToPermissions(Long permissionsMask) {
        if (permissionsMask == null) {
            return null;
        }

        return PermissionUtils.toEnumSet(permissionsMask);
    }

    default Long mapPermissionsToLong(Set<Permission> permissions) {
        if (permissions == null) {
            return 0L;
        }

        return PermissionUtils.toBitmask(permissions);
    }
}

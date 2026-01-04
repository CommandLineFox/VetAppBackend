package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.dto.VeterinarianDto;

@Component
public class VeterinarianMapper {
    public VeterinarianDto veterinarianToVeterinarianDto(raf.aleksabuncic.domain.Veterinarian veterinarian) {
        VeterinarianDto veterinarianDto = new VeterinarianDto();

        veterinarianDto.setId(veterinarian.getId());
        veterinarianDto.setFirstName(veterinarian.getFirstName());
        veterinarianDto.setLastName(veterinarian.getLastName());
        veterinarianDto.setLicenseNumber(veterinarian.getLicenseNumber());
        veterinarianDto.setPassword(veterinarian.getPassword());

        return veterinarianDto;
    }
}

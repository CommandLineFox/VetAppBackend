package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianDto;

@Component
public class VeterinarianMapper {
    public VeterinarianDto veterinarianToVeterinarianDto(Veterinarian veterinarian) {
        VeterinarianDto veterinarianDto = new VeterinarianDto();

        veterinarianDto.setId(veterinarian.getId());
        veterinarianDto.setFirstName(veterinarian.getFirstName());
        veterinarianDto.setLastName(veterinarian.getLastName());
        veterinarianDto.setLicenseNumber(veterinarian.getLicenseNumber());
        veterinarianDto.setPassword(veterinarian.getPassword());

        return veterinarianDto;
    }

    public Veterinarian veterinarianCreateDtoToVeterinarian(VeterinarianCreateDto veterinarianCreateDto) {
        Veterinarian veterinarian = new Veterinarian();

        veterinarian.setFirstName(veterinarianCreateDto.getFirstName());
        veterinarian.setLastName(veterinarianCreateDto.getLastName());
        veterinarian.setLicenseNumber(veterinarianCreateDto.getLicenseNumber());

        return veterinarian;
    }
}

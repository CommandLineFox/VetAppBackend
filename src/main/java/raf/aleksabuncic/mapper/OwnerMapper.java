package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.dto.OwnerRequestDto;
import raf.aleksabuncic.dto.OwnerDto;

@Component
public class OwnerMapper {
    public OwnerDto ownerToOwnerDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setId(owner.getId());
        ownerDto.setFirstName(owner.getFirstName());
        ownerDto.setLastName(owner.getLastName());
        ownerDto.setAddress(owner.getAddress());
        ownerDto.setPhoneNumber(owner.getPhoneNumber());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setJmbg(owner.getJmbg());

        return ownerDto;
    }

    public Owner ownerRequestDtoToOwner(OwnerRequestDto ownerRequestDto) {
        Owner owner = new Owner();

        owner.setFirstName(ownerRequestDto.getFirstName());
        owner.setLastName(ownerRequestDto.getLastName());
        owner.setAddress(ownerRequestDto.getAddress());
        owner.setPhoneNumber(ownerRequestDto.getPhoneNumber());
        owner.setEmail(ownerRequestDto.getEmail());
        owner.setJmbg(ownerRequestDto.getJmbg());

        return owner;
    }
}

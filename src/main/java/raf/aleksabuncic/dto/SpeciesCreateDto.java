package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpeciesCreateDto {
    @NotBlank
    @Size(max = 50)
    private String name;
}

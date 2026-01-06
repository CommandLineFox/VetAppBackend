package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedCreateDto {
    @NotBlank
    private String name;

    @NotNull
    private Long speciesId;
}

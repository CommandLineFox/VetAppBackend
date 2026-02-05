package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpeciesUpdateDto {
    @Size(max = 50)
    @Pattern(regexp = ".*\\S.*")
    private String name;
}

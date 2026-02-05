package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BreedUpdateDto {
    @Pattern(regexp = ".*\\S.*")
    private String name;

    private Long speciesId;
}

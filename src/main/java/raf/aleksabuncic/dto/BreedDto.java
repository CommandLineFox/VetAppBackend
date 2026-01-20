package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BreedDto {
    private Long id;
    private String name;
    private Long speciesId;
}

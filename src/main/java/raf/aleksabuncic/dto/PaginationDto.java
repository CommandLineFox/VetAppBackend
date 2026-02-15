package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaginationDto {
    @PositiveOrZero
    private Integer page;

    @Positive
    private Integer size;

    @Pattern(regexp = ".*\\S.*")
    private String sortBy;

    @Pattern(regexp = "(?i)asc|desc")
    private String direction;
}

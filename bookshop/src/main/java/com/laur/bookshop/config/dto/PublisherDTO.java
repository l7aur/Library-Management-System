package com.laur.bookshop.config.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Data
public class PublisherDTO {
    private UUID id;

    @NotEmpty(message = PUBLISHER_NAME_MISSING_MESSAGE)
    @Size(min = PUBLISHER_NAME_LENGTH_MIN, max = PUBLISHER_NAME_LENGTH_MAX, message = PUBLISHER_NAME_LENGTH_NOT_VALID_MESSAGE)
    private String name;

    @NotEmpty(message = PUBLISHER_LOCATION_MISSING_MESSAGE)
    @Size(min = PUBLISHER_LOCATION_LENGTH_MIN, max = PUBLISHER_LOCATION_LENGTH_MAX, message = PUBLISHER_LOCATION_LENGTH_NOT_VALID_MESSAGE)
    private String location;

    @Min(value = PUBLISHER_FOUNDING_YEAR_MIN, message = PUBLISHER_FOUNDING_YEAR_NOT_VALID_MESSAGE)
    @Max(value = PUBLISHER_FOUNDING_YEAR_MAX, message = PUBLISHER_FOUNDING_YEAR_NOT_VALID_MESSAGE)
    private Integer foundingYear;

    private List<String> bookIds;
}

package com.laur.bookshop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PublisherCreateDTO {
    @NotEmpty(message = "Publisher name is mandatory!")
    @Size(min = 2, max = 50, message = "Publisher name must be between 2 and 50 characters!")
    private String name;

    @NotEmpty(message = "Publisher location is mandatory!")
    @Size(min = 2, max = 50, message = "Publisher location must be between 2 and 50 characters!")
    private String location;

    @Min(value = 1500, message = "No publisher can be that old!")
    @Max(value = 2025, message = "No publisher could have been found in the future!")
    private Integer foundingYear;

    private List<String> books;
}

package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.isbn.IsbnValidator;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Data
public class BookDTO {
    private UUID id;

    @IsbnValidator
    private String isbn;

    @NotBlank(message = MISSING_TITLE_MESSAGE)
    @Size(min = TITLE_MIN_LENGTH_MESSAGE, max = TITLE_MAX_LENGTH_MESSAGE, message = TITLE_SIZE_NOT_VALID_MESSAGE)
    private String title;

    @NotNull(message = MISSING_PUBLISH_YEAR_MESSAGE)
    @Min(value = PUBLISH_YEAR_MIN_MESSAGE, message = PUBLISH_YEAR_NOT_VALID_MESSAGE)
    @Max(value = PUBLISH_YEAR_MAX_MESSAGE, message = PUBLISH_YEAR_NOT_VALID_MESSAGE)
    private Integer publishYear;

    @NotNull(message = MISSING_PUBLISHER_ID_MESSAGE)
    private String publisherId;

    @NotEmpty(message = MISSING_AUTHOR_IDS_MESSAGE)
    private List<String> authorIds;

    @Positive(message = PRICE_NOT_VALID_MESSAGE)
    @NotNull(message = MISSING_PRICE_MESSAGE)
    private Double price;

    @Positive(message = STOCK_NOT_VALID_MESSAGE)
    @NotNull(message = MISSING_STOCK_MESSAGE)
    private Integer stock;
}

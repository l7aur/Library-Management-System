package com.laur.bookshop.config.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Data
public class AuthorDTO {
    private UUID id;

    @NotBlank(message = AUTHOR_FIRST_NAME_MISSING_MESSAGE)
    @Size(min = AUTHOR_FIRST_NAME_MIN_LENGTH, max = AUTHOR_FIRST_NAME_MAX_LENGTH, message = AUTHOR_FIRST_NAME_NOT_VALID_MESSAGE)
    private String firstName;

    @NotBlank(message = AUTHOR_LAST_NAME_MISSING_MESSAGE)
    @Size(min = AUTHOR_LAST_NAME_MIN_LENGTH, max = AUTHOR_LAST_NAME_MAX_LENGTH, message = AUTHOR_LAST_NAME_NOT_VALID_MESSAGE)
    private String lastName;

    @Size(max = AUTHOR_ALIAS_MAX_LENGTH, message = AUTHOR_ALIAS_NOT_VALID_MESSAGE)
    private String alias;

    @NotBlank(message = AUTHOR_NATIONALITY_MISSING_MESSAGE)
    @Size(min = AUTHOR_NATIONALITY_MIN_LENGTH, max = AUTHOR_NATIONALITY_MAX_LENGTH, message = AUTHOR_NATIONALITY_NOT_VALID_MESSAGE)
    private String nationality;

    List<String> bookIDs;
}

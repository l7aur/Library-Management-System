package com.laur.bookshop.config.enums;

public final class AppMessages {

    private AppMessages() {}

    public static final String PUBLISHER_NOT_FOUND_MESSAGE = "Publisher not found!";
    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Author not found!";
    public static final String BOOK_NOT_FOUND_MESSAGE = "Book not found!";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found!";
    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password!";

    public static final String PUBLISHER_DUPLICATE_MESSAGE ="Publisher already exists!";
    public static final String BOOK_DUPLICATE_MESSAGE = "Book already exists!";
    public static final String AUTHOR_DUPLICATE_MESSAGE = "Author already exists!";
    public static final String USER_DUPLICATE_MESSAGE = "User already exists!";

    public static final String BOOK_DELETE_SUCCESS_MESSAGE = "Book deleted successfully!";
    public static final String PUBLISHER_DELETE_SUCCESS_MESSAGE = "Publisher deleted successfully!";
    public static final String AUTHOR_DELETE_SUCCESS_MESSAGE = "Author deleted successfully!";
    public static final String USER_DELETE_SUCCESS_MESSAGE = "User deleted successfully!";
    public static final String BOOK_DELETE_ERROR_MESSAGE = "Book deletion failed!";
    public static final String PUBLISHER_DELETE_ERROR_MESSAGE = "Publisher deletion failed!";
    public static final String AUTHOR_DELETE_ERROR_MESSAGE = "Author deletion failed!";
    public static final String USER_DELETE_ERROR_MESSAGE = "User deletion failed!";

    public static final String MISSING_TITLE_MESSAGE = "The title is mandatory!";
    public static final int TITLE_MIN_LENGTH_MESSAGE = 2;
    public static final int TITLE_MAX_LENGTH_MESSAGE = 255;
    public static final String TITLE_SIZE_NOT_VALID_MESSAGE = "The title must have between 2 and 255 characters!";

    public static final String MISSING_PUBLISH_YEAR_MESSAGE = "The publish year is mandatory!";
    public static final int PUBLISH_YEAR_MIN_MESSAGE = 1000;
    public static final int PUBLISH_YEAR_MAX_MESSAGE = 2025;
    public static final String PUBLISH_YEAR_NOT_VALID_MESSAGE = "The publish year is not valid!";

    public static final String MISSING_PUBLISHER_ID_MESSAGE = "The publisher is mandatory!";

    public static final String MISSING_AUTHOR_IDS_MESSAGE = "The authors are mandatory!";

    public static final String PRICE_NOT_VALID_MESSAGE = "The price is not valid!";
    public static final String MISSING_PRICE_MESSAGE = "The price is mandatory!";

    public static final String STOCK_NOT_VALID_MESSAGE = "The stock is not valid!";
    public static final String MISSING_STOCK_MESSAGE = "The stock is mandatory!";

    public static final String FAILED_LOADING_JSON_DATA_MESSAGE = "Failed to load seed data JSON file!";
    public static final String FAILED_SEEDING_DATABASE_MESSAGE = "An error occurred while seeding the database!";
    public static final String JSON_STRUCTURE_ERROR_MESSAGE = "Invalid book json file!";

    public static final String PUBLISHER_NAME_MISSING_MESSAGE = "Publisher name is mandatory!";
    public static final String PUBLISHER_LOCATION_MISSING_MESSAGE = "Publisher location is mandatory!";
    public static final String PUBLISHER_FOUNDING_YEAR_NOT_VALID_MESSAGE = "Founding year is not valid!";
    public static final int PUBLISHER_FOUNDING_YEAR_MIN = 1000;
    public static final int PUBLISHER_FOUNDING_YEAR_MAX = 2025;
    public static final int PUBLISHER_LOCATION_LENGTH_MAX = 50;
    public static final int PUBLISHER_LOCATION_LENGTH_MIN = 2;
    public static final String PUBLISHER_LOCATION_LENGTH_NOT_VALID_MESSAGE = "Publisher location must be between 2 and 50 characters!";

    public static final int PUBLISHER_NAME_LENGTH_MAX = 50;
    public static final int PUBLISHER_NAME_LENGTH_MIN = 2;
    public static final String PUBLISHER_NAME_LENGTH_NOT_VALID_MESSAGE = "Publisher name must be between 2 and 50 characters!";

    public static final String AUTHOR_FIRST_NAME_MISSING_MESSAGE = "First name is mandatory!";
    public static final String AUTHOR_LAST_NAME_MISSING_MESSAGE = "Last name is mandatory!";
    public static final String AUTHOR_NATIONALITY_MISSING_MESSAGE = "Nationality is mandatory!";
    public static final int AUTHOR_FIRST_NAME_MIN_LENGTH = 2;
    public static final int AUTHOR_FIRST_NAME_MAX_LENGTH = 50;
    public static final int AUTHOR_LAST_NAME_MIN_LENGTH = 2;
    public static final int AUTHOR_LAST_NAME_MAX_LENGTH = 50;
    public static final String AUTHOR_FIRST_NAME_NOT_VALID_MESSAGE = "First name must be between 2 and 50 characters!";
    public static final String AUTHOR_LAST_NAME_NOT_VALID_MESSAGE = "Last name must be between 2 and 50 characters!";
    public static final String AUTHOR_ALIAS_NOT_VALID_MESSAGE = "Alias cannot exceed 50 characters!";
    public static final String AUTHOR_NATIONALITY_NOT_VALID_MESSAGE = "Nationality should be between 2 and 50 characters!";
    public static final int AUTHOR_ALIAS_MAX_LENGTH = 50;
    public static final int AUTHOR_NATIONALITY_MIN_LENGTH = 2;
    public static final int AUTHOR_NATIONALITY_MAX_LENGTH = 50;

    public static final String PASSWORD_VALIDATOR_ERROR_MESSAGE_DIGIT = "Password must contain at least one digit!";
    public static final String PASSWORD_VALIDATOR_ERROR_MESSAGE_SPECIAL = "Password must contain at least one special character!";
    public static final String PASSWORD_VALIDATOR_ERROR_MESSAGE_LOWERCASE = "Password must contain at least one lowercase letter!";
    public static final String PASSWORD_VALIDATOR_ERROR_MESSAGE_UPPERCASE = "Password must contain at least one uppercase letter!";
    public static final String PASSWORD_VALIDATOR_ERROR_MESSAGE_MISSING = "Password cannot be empty!";
}

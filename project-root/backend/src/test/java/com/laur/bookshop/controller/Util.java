package com.laur.bookshop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Util {
    static public String loadFixture(String basePath, String fileName) throws IOException {
        return Files.readString(Paths.get(basePath + fileName));
    }
}

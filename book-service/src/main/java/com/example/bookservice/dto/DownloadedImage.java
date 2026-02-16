package com.example.bookservice.dto;

import java.io.InputStream;

public record DownloadedImage(
        InputStream inputStream,
        String filename,
        String contentType,
        long length
) {
}

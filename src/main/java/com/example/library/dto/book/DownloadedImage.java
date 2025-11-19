package com.example.library.dto.book;

import java.io.InputStream;

public record DownloadedImage(
        InputStream inputStream,
        String filename,
        String contentType,
        long length
) {
}

package com.example.library.service;

import com.example.library.dto.book.DownloadedImage;

import java.io.InputStream;

public interface BookImageService {
    void uploadImage(long bookId, InputStream data, String filename, String contentType);
    DownloadedImage downloadImage(long bookId);
}

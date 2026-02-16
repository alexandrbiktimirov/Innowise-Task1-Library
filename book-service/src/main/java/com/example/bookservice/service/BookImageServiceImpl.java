package com.example.bookservice.service;

import com.example.library.dto.book.DownloadedImage;
import com.example.library.exception.BookDoesNotExistException;
import com.example.library.exception.BookImageDoesNotExistException;
import com.example.library.exception.ImageNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class BookImageServiceImpl implements BookImageService {
    private final BookRepository bookRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFsBucket;

    public BookImageServiceImpl(BookRepository bookRepository, GridFsTemplate gridFsTemplate, GridFSBucket gridFsBucket) {
        this.bookRepository = bookRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsBucket = gridFsBucket;
    }

    @Override
    @Transactional
    public void uploadImage(long bookId, InputStream data, String filename, String contentType) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book with id: " + bookId + "does not exist"));

        if (book.getImageId() != null){
            ObjectId oldId = new ObjectId(book.getImageId());
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(oldId)));
        }

        ObjectId storedId = gridFsTemplate.store(data, filename, contentType);

        book.setImageId(storedId.toHexString());
        bookRepository.save(book);
    }

    @Override
    public DownloadedImage downloadImage(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book with id: " + bookId + "does not exist"));

        if (book.getImageId() == null){
            throw new BookImageDoesNotExistException("Book with id: " + bookId + " does not have an image");
        }

        ObjectId fileId = new ObjectId(book.getImageId());

        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));

        if (gridFsFile == null){
            throw new ImageNotFoundException("Image for the book with id: " + fileId + " was not found in the database");
        }

        GridFSDownloadStream downloadStream = gridFsBucket.openDownloadStream(fileId);
        String filename = Optional.of(gridFsFile.getFilename()).orElse("file");
        long length = gridFsFile.getLength();
        String contentType = Optional.ofNullable(gridFsFile.getMetadata())
                .map(m -> m.getString("_contentType"))
                .orElse("application/octet-stream");


        return new DownloadedImage(
                downloadStream,
                filename,
                contentType,
                length
        );
    }
}

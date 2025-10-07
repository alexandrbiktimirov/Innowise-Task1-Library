package model;

import org.springframework.data.annotation.Id;

public class Book {
    @Id
    private Long id;
    private String title;
    private long authorId;
    private String description;
    private long genreId;

    public Book(String title, long author, String description, long genre) {
        this.title = title;
        this.authorId = author;
        this.description = description;
        this.genreId = genre;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Title: " + title + ", Author id: " + authorId + ", Description: " + description + ". Genre id: " + genreId;
    }
}

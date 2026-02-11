package dto;

public record GenreDto(Long id, String name) {
    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name;
    }
}

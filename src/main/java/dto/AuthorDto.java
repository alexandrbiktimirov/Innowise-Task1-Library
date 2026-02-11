package dto;

public record AuthorDto(Long id, String firstName, String lastName) {
    @Override
    public String toString() {
        return "Id: " + id + ", First Name: " + firstName + ", Last Name: " + lastName;
    }
}

import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookUpdateDto;
import com.example.library.mapper.AuthorMapper;
import com.example.library.model.Author;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    @Test
    public void givenAuthor_whenToDto_thenCorrect(){
        Author author = new Author("John", "Doe");
        AuthorDto authorDto = new AuthorDto("John", "Doe");

        assertEquals(authorDto, AuthorMapper.INSTANCE.toDto(author));
    }

    @Test
    public void givenAuthorDto_whenToEntity_thenCorrect(){
        AuthorCreateDto authorDto = new AuthorCreateDto("John", "Doe");
        Author author = new Author("John", "Doe");

        assertEquals(author, AuthorMapper.INSTANCE.toEntity(authorDto));
    }

    @Test
    public void givenValidBookParameters_whenCreateBookDto_thenNoViolationsPresent(){
        var bookCreateDto = new BookCreateDto("Some title", 1, "Some description", 1);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<BookCreateDto>> constraintViolations = validator.validate(bookCreateDto);

        assertTrue(constraintViolations.isEmpty());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenValidBookParameters_whenUpdateBookDto_thenNoViolationsPresent(){
        var BookUpdateDto = new BookUpdateDto("Some title", 1, "Some description", 1);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<BookUpdateDto>> constraintViolations = validator.validate(BookUpdateDto);

        assertTrue(constraintViolations.isEmpty());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenInvalidBookParameters_whenCreateBookDto_thenViolationsPresent(){
        var bookCreateDto = new BookCreateDto("", 0, "", 0);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<BookCreateDto>> constraintViolations = validator.validate(bookCreateDto);

        assertFalse(constraintViolations.isEmpty());
        assertEquals(4, constraintViolations.size());
    }
}

import com.example.library.model.Role;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class IntegrationTests extends BaseIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void givenUserSaved_whenFindByUsername_thenFound() {
        var u = new User();
        u.setUsername("john");
        u.setPassword(passwordEncoder.encode("secret"));
        u.setRoles(Set.of(Role.USER));
        userRepository.save(u);

        var loaded = userRepository.findByUsername("john");
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getUsername()).isEqualTo("john");
    }

    @Test
    public void whenRegister_thenPasswordStoredHashed() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"username":"alice","password":"alice123"}
                        """))
                .andExpect(status().isOk());

        var saved = userRepository.findByUsername("alice").orElseThrow();

        assertThat(saved.getPassword()).isNotEqualTo("alice123");
        assertThat(passwordEncoder.matches("alice123", saved.getPassword())).isTrue();
    }

    @Test
    public void whenLogin_thenReturnsBearerTokenHeader() throws Exception {
        var u = new User();
        u.setUsername("bob");
        u.setPassword(passwordEncoder.encode("bob123"));
        u.setRoles(Set.of(Role.USER));
        userRepository.save(u);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"username":"bob","password":"bob123"}
                        """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));
    }

    @Test
    public void whenAccessProtectedWithoutToken_then401() throws Exception {
        mockMvc.perform(get("/api/v1/books")).andExpect(status().isUnauthorized());
    }

    @Test
    public void deletionEndpoint_accessDependsOnRole() throws Exception {
        var user = new User();
        user.setUsername("user1");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRoles(Set.of(Role.USER));
        userRepository.save(user);

        var admin = new User();
        admin.setUsername("admin1");
        admin.setPassword(passwordEncoder.encode("pass"));
        admin.setRoles(Set.of(Role.ADMIN));
        userRepository.save(admin);

        String userToken = loginAndGetToken("user1", "pass");
        String adminToken = loginAndGetToken("admin1", "pass");

        mockMvc.perform(post("/api/v1/authors")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"firstName":"John","lastName":"Doe"}
                """))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/v1/authors")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"firstName":"John","lastName":"Doe"}
                """))
                .andExpect(status().isOk());
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        var result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"%s"}
                                """.formatted(username, password)))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getHeader("Authorization");
    }
}
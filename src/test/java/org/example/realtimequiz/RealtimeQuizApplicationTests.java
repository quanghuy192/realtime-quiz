package org.example.realtimequiz;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.example.realtimequiz.models.entity.QuizUser;
import org.example.realtimequiz.models.repository.QuizUserPostgresRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RealtimeQuizApplication.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RealtimeQuizApplicationTests {

    private ObjectMapper mapper;
    private ObjectWriter ow;
    private Faker faker;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private QuizUserPostgresRepository quizUserPostgresRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        // delete all user
        quizUserPostgresRepository.deleteAll();

        // config mapper
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();

        // config faker
        faker = new Faker(Locale.US);
    }

    @Test
    public void givenBatchRandomUser_whenRegisterNewRandomScore_thenNewNewRandomScoreSuccess() throws Exception {
        // create 5000 new user ID to register score into systems
        var usersSaved = new HashMap<UUID, QuizUser>();
        var usersIdsRandom = Instancio.ofList(UUID.class).size(5000)
                .create();
        var random = new Random();

        // register new score User for 100000 times
        for (int i = 0; i < 10; i++) {
            var index = random.nextInt(0, usersIdsRandom.size() - 1);
            var userId = usersIdsRandom.get(index);
            var newScore = random.nextLong(0, 9999999L);

            String requestBody;
            if (usersSaved.containsKey(userId)) {
                var oldUser = usersSaved.get(userId);
                oldUser.setScore(newScore);
                oldUser.setUpdatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                requestBody = ow.writeValueAsString(oldUser);
            } else {
                var newUser = createUser(userId, newScore);
                usersSaved.put(userId, newUser);
                requestBody = ow.writeValueAsString(newUser);
            }

            mockMvc.perform(
                            post("/api/v1/user/score")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isOk());
        }

        Thread.sleep(10000);
    }

    // Utilities class
    private QuizUser createUser(UUID userId, long score) {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return QuizUser.builder()
                .id(userId)
                .name(faker.name().fullName())
                .score(score)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}

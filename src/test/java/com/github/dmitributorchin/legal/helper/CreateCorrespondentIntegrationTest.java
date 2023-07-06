package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.correspondent.Correspondent;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentController;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCorrespondentIntegrationTest {
    @Autowired
    private CorrespondentController correspondentController;

    private WebTestClient webClient;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebTestClient.bindToController(correspondentController)
                .controllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void performsValidation() {
        var response = webClient.post().uri("/correspondents")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(JsonResponse.class)
                .getResponseBody()
                .blockFirst();
        var errors = response.errors().stream()
                .collect(Collectors.toMap(
                        (JsonError error) -> error.source().pointer(),
                        JsonError::title
                ));
        assertThat(errors).containsExactlyInAnyOrderEntriesOf(Map.of(
                "/createCorrespondent/title", "must not be blank"
        ));
    }

    @Test
    public void createsCorrespondent() {
        var correspondents = webClient.get().uri("/correspondents")
                .exchange()
                .expectBodyList(Correspondent.class)
                .returnResult()
                .getResponseBody();
        assertThat(correspondents).hasSize(1);

        var correspondent = webClient.post().uri("/correspondents")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"title\":\"Correspondent\"" +
                        "}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(CorrespondentCreated.class)
                .getResponseBody()
                .blockFirst();
        assertThat(correspondent.id()).isNotNull();
        assertThat(correspondent.title()).isEqualTo("Correspondent");

        correspondents = webClient.get().uri("/correspondents")
                .exchange()
                .expectBodyList(Correspondent.class)
                .returnResult()
                .getResponseBody();
        assertThat(correspondents).hasSize(2);
    }
}

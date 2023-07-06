package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.region.Region;
import com.github.dmitributorchin.legal.helper.region.RegionController;
import com.github.dmitributorchin.legal.helper.region.RegionCreated;
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
public class CreateRegionIntegrationTest {
    @Autowired
    private RegionController regionController;

    private WebTestClient webClient;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebTestClient.bindToController(regionController)
                .controllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void performsValidation() {
        var response = webClient.post().uri("/regions")
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
                "/createRegion/title", "must not be blank"
        ));
    }

    @Test
    public void createsRegion() {
        var regions = webClient.get().uri("/regions")
                .exchange()
                .expectBodyList(Region.class)
                .returnResult()
                .getResponseBody();
        assertThat(regions).hasSize(1);

        var correspondent = webClient.post().uri("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"title\":\"Region\"" +
                        "}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(RegionCreated.class)
                .getResponseBody()
                .blockFirst();
        assertThat(correspondent.id()).isNotNull();
        assertThat(correspondent.title()).isEqualTo("Region");

        regions = webClient.get().uri("/regions")
                .exchange()
                .expectBodyList(Region.class)
                .returnResult()
                .getResponseBody();
        assertThat(regions).hasSize(2);
    }
}

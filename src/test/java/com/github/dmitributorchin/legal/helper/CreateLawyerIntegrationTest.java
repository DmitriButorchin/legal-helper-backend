package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.lawyer.LawyerController;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerCreated;
import com.github.dmitributorchin.legal.helper.region.Region;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import com.github.dmitributorchin.legal.helper.region.RegionRepository;
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

// TODO: refactor integration tests
// TODO: change repository calls to create something with rest calls
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateLawyerIntegrationTest {
    @Autowired
    private LawyerController lawyerController;

    private WebTestClient webClient;

    @Autowired
    private RegionRepository regionRepository;

    private String regionId;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebTestClient.bindToController(lawyerController)
                .controllerAdvice(GlobalExceptionHandler.class)
                .build();

        var region = new RegionEntity();
        regionRepository.save(region);
        regionId = region.getId().toString();
    }

    @Test
    public void performsValidation() {
        var response = webClient.post().uri("/lawyers")
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
                "/createLawyer/ssn", "must not be blank",
                "/createLawyer/firstName", "must not be blank",
                "/createLawyer/lastName", "must not be blank",
                "/createLawyer/regionId", "must not be blank"
        ));
    }

    @Test
    public void createsLawyer() {
        var lawyers = webClient.get().uri("/lawyers")
                .exchange()
                .expectBodyList(Region.class)
                .returnResult()
                .getResponseBody();
        var size = lawyers.size();

        var lawyer = webClient.post().uri("/lawyers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"ssn\":\"A102\"," +
                        "   \"firstName\":\"Jim\"," +
                        "   \"lastName\":\"Beam\"," +
                        "   \"regionId\":\"" + regionId + "\"" +
                        "}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(LawyerCreated.class)
                .getResponseBody()
                .blockFirst();
        assertThat(lawyer.ssn()).isEqualTo("A102");
        assertThat(lawyer.firstName()).isEqualTo("Jim");
        assertThat(lawyer.lastName()).isEqualTo("Beam");
        assertThat(lawyer.regionId()).isEqualTo(regionId);
        assertThat(lawyer.claimCount()).isEqualTo(0);

        lawyers = webClient.get().uri("/lawyers")
                .exchange()
                .expectBodyList(Region.class)
                .returnResult()
                .getResponseBody();
        assertThat(lawyers).hasSize(size + 1);
    }
}

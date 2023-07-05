package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.claim.ClaimController;
import com.github.dmitributorchin.legal.helper.claim.ClaimCreated;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentEntity;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentRepository;
import com.github.dmitributorchin.legal.helper.lawyer.GetLawyer;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerController;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateClaimIntegrationTest {
    @Autowired
    private ClaimController claimController;

    @Autowired
    private LawyerController lawyerController;

    @Autowired
    private CorrespondentRepository correspondentRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    private WebTestClient webClient;

    private String correspondentId;
    private String regionId;
    private String lawyerId;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebTestClient.bindToController(claimController, lawyerController)
                .controllerAdvice(GlobalExceptionHandler.class)
                .build();

        var correspondent = new CorrespondentEntity();
        correspondentRepository.save(correspondent);
        correspondentId = correspondent.getId().toString();

        var region = new RegionEntity();
        regionRepository.save(region);
        regionId = region.getId().toString();

        var lawyer = new LawyerEntity();
        lawyer.setRegion(region);
        lawyerRepository.save(lawyer);
        lawyerId = lawyer.getId().toString();
    }

    @Test
    public void performsValidation() {
        var response = webClient.post().uri("/claims")
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
                "/createClaim/registrationNumber", "must not be blank",
                "/createClaim/correspondentId", "must not be blank",
                "/createClaim/creationDate", "must not be null",
                "/createClaim/creationNumber", "must not be blank",
                "/createClaim/summary", "must not be blank",
                "/createClaim/responsible", "must not be blank",
                "/createClaim/regionId", "must not be blank",
                "/createClaim/lawyerId", "must not be blank",
                "/createClaim/defendant", "must not be blank",
                "/createClaim/deadline", "must not be null"
        ));
    }

    @Test
    public void translatesDomainException() {
        var response = webClient.post().uri("/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"registrationNumber\": \"1\"," +
                        "   \"correspondentId\": \"1\"," +
                        "   \"creationDate\": \"1999-01-01\"," +
                        "   \"creationNumber\": \"1\"," +
                        "   \"summary\": \"1\"," +
                        "   \"responsible\": \"1\"," +
                        "   \"regionId\": \"1\"," +
                        "   \"lawyerId\": \"1\"," +
                        "   \"defendant\": \"1\"," +
                        "   \"deadline\": \"1999-01-01\"" +
                        "}")
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(JsonResponse.class)
                .getResponseBody()
                .blockFirst();
        var errorMessages = response.errors().stream().map(JsonError::title).toList();
        assertThat(errorMessages).containsExactlyInAnyOrder("Claim with specified registration number already exists");
        var errorPointers = response.errors().stream().map(error -> error.source().pointer()).toList();
        assertThat(errorPointers).containsExactlyInAnyOrder("/createClaim/registrationNumber");
    }

    @Test
    public void createsClaim() {
        var lawyer = webClient.get().uri("/lawyers/" + lawyerId)
                .exchange()
                .returnResult(GetLawyer.class)
                .getResponseBody()
                .blockFirst();
        assertThat(lawyer.claimCount()).isEqualTo(0);

        var claim = webClient.post().uri("/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"registrationNumber\":\"6\"," +
                        "   \"correspondentId\":\"" + correspondentId + "\"," +
                        "   \"creationDate\":\"2023-07-01\"," +
                        "   \"creationNumber\":\"1\"," +
                        "   \"summary\":\"IOU\"," +
                        "   \"responsible\":\"Ivan\"," +
                        "   \"regionId\":\"" + regionId + "\"," +
                        "   \"lawyerId\":\"" + lawyerId + "\"," +
                        "   \"defendant\":\"Mike\"," +
                        "   \"deadline\":\"2023-11-01\"" +
                        "}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(ClaimCreated.class)
                .getResponseBody()
                .blockFirst();
        assertThat(claim.registrationDate()).isEqualTo("2023-07-05"); // TODO: mock date
        assertThat(claim.registrationNumber()).isEqualTo("6");
        assertThat(claim.correspondentId()).isEqualTo(correspondentId);
        assertThat(claim.creationDate()).isEqualTo("2023-07-01");
        assertThat(claim.creationNumber()).isEqualTo("1");
        assertThat(claim.summary()).isEqualTo("IOU");
        assertThat(claim.responsible()).isEqualTo("Ivan");
        assertThat(claim.regionId()).isEqualTo(regionId);
        assertThat(claim.lawyerId()).isEqualTo(lawyerId);
        assertThat(claim.defendant()).isEqualTo("Mike");
        assertThat(claim.deadline()).isEqualTo("2023-11-01");

        lawyer = webClient.get().uri("/lawyers/" + lawyerId)
                .exchange()
                .returnResult(GetLawyer.class)
                .getResponseBody()
                .blockFirst();
        assertThat(lawyer.claimCount()).isEqualTo(1);
    }
}

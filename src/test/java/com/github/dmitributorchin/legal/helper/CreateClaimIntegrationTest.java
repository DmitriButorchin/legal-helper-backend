package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.agency.AgencyRepository;
import com.github.dmitributorchin.legal.helper.claim.Claim;
import com.github.dmitributorchin.legal.helper.claim.ClaimController;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateClaimIntegrationTest {
    @Autowired
    private ClaimController claimController;

    @Autowired
    private LawyerController lawyerController;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    private WebTestClient webClient;

    private String agencyId;
    private String regionId;
    private String lawyerId;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebTestClient.bindToController(claimController, lawyerController)
                .controllerAdvice(GlobalExceptionHandler.class)
                .build();

        var agency = new AgencyEntity();
        agencyRepository.save(agency);
        agencyId = agency.getId().toString();

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
        var errorMessages = response.errors().stream().map(JsonError::title).toList();
        assertThat(errorMessages).containsExactlyInAnyOrder(
                "must not be blank",
                "must not be blank",
                "must not be blank",
                "must not be blank"
        );
        var errorPointers = response.errors().stream().map(error -> error.source().pointer()).toList();
        assertThat(errorPointers).containsExactlyInAnyOrder(
                "/createClaim/number",
                "/createClaim/agencyId",
                "/createClaim/regionId",
                "/createClaim/lawyerId"
        );
    }

    @Test
    public void translatesDomainException() {
        var response = webClient.post().uri("/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("" +
                        "{" +
                        "   \"number\": \"1\"," +
                        "   \"agencyId\": \"1\"," +
                        "   \"regionId\": \"1\"," +
                        "   \"lawyerId\": \"1\"" +
                        "}")
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(JsonResponse.class)
                .getResponseBody()
                .blockFirst();
        var errorMessages = response.errors().stream().map(JsonError::title).toList();
        assertThat(errorMessages).containsExactlyInAnyOrder("Claim with specified number already exists");
        var errorPointers = response.errors().stream().map(error -> error.source().pointer()).toList();
        assertThat(errorPointers).containsExactlyInAnyOrder("/createClaim/number");
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
                        "   \"number\":\"6\"," +
                        "   \"agencyId\":\"" + agencyId + "\"," +
                        "   \"regionId\":\"" + regionId + "\"," +
                        "   \"lawyerId\":\"" + lawyerId + "\"" +
                        "}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Claim.class)
                .getResponseBody()
                .blockFirst();
        assertThat(claim.number()).isEqualTo("6");
        assertThat(claim.agencyId()).isEqualTo(agencyId);
        assertThat(claim.regionId()).isEqualTo(regionId);
        assertThat(claim.lawyerId()).isEqualTo(lawyerId);

        lawyer = webClient.get().uri("/lawyers/" + lawyerId)
                .exchange()
                .returnResult(GetLawyer.class)
                .getResponseBody()
                .blockFirst();
        assertThat(lawyer.claimCount()).isEqualTo(1);
    }
}

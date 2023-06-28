package com.github.dmitributorchin.legal.helper.claim;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerEntity;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "claim")
@Getter
@Setter
public class ClaimEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private AgencyEntity agency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    @ManyToOne
    @JoinColumn(name = "lawyer_id")
    private LawyerEntity lawyer;
}

package com.github.dmitributorchin.legal.helper.lawyer;

import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "lawyer")
@Getter
@Setter
public class LawyerEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String ssn;
    private String firstName;
    private String lastName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;
}

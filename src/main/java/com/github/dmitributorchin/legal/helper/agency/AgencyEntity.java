package com.github.dmitributorchin.legal.helper.agency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "agency")
@Getter
@Setter
public class AgencyEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
}

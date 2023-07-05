package com.github.dmitributorchin.legal.helper.correspondent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "correspondent")
@Getter
@Setter
public class CorrespondentEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
}

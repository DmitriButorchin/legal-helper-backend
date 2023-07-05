package com.github.dmitributorchin.legal.helper.claim;

import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerEntity;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "claim")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ClaimEntity implements Persistable<String> {
    /**
     * When the claim was registered in the court
     */
    @CreatedDate
    private LocalDate registrationDate;

    /**
     * Number assigned in the court
     */
    @Id
    private String registrationNumber;

    /**
     * Agency/person who sent the claim
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "correspondent_id", nullable = false)
    private CorrespondentEntity correspondent;

    /**
     * When claim was created by the correspondent
     */
    @Column(nullable = false)
    private LocalDate creationDate;

    /**
     * Number assigned by the correspondent
     */
    @Column(nullable = false)
    private String creationNumber;

    /**
     * Summary of the claim
     */
    @Column(nullable = false)
    private String summary;

    /**
     * Person responsible for assignment of the claim
     */
    @Column(nullable = false)
    private String responsible;

    /**
     * In what region the claim is registered
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    /**
     * Lawyer assigned to handle the claim
     */
    @ManyToOne
    @JoinColumn(name = "lawyer_id")
    private LawyerEntity lawyer;

    /**
     * Against whom the claim is brought
     */
    @Column(nullable = false)
    private String defendant;

    /**
     * When claim is expected to be resolved
     */
    @Column(nullable = false)
    private LocalDate deadline;

    @Override
    public String getId() {
        return getRegistrationNumber();
    }

    @Override
    public boolean isNew() {
        return getRegistrationDate() == null;
    }
}

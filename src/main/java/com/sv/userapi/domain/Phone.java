package com.sv.userapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sv.userapi.domain.dto.PhoneDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Phone.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "phone")
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "number")
    private String number;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "phones" }, allowSetters = true)
    private User user;

    public static PhoneDTO toDto(Phone phone){
        return PhoneDTO.builder()
                .id(phone.getId())
                .cityCode(phone.getCityCode())
                .number(phone.getNumber())
                .countryCode(phone.getCountryCode())
                .build();
    }

    public static Phone toEntity(PhoneDTO phoneDTO){
        return Phone.builder()
                .id(phoneDTO.id())
                .number(phoneDTO.number())
                .cityCode(phoneDTO.cityCode())
                .countryCode(phoneDTO.countryCode())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        return getId() != null && getId().equals(((Phone) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Phone{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}

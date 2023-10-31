package com.sv.userapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sv.userapi.domain.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A User.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SuppressWarnings("common-java:DuplicatedBlocks")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userE")
    @JsonIgnoreProperties(value = { "userE" }, allowSetters = true)
    private Set<Phone> phones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public void setPhones(Set<Phone> phones) {
        if (this.phones != null) {
            this.phones.forEach(i -> i.setUser(null));
        }
        if (phones != null) {
            phones.forEach(i -> i.setUser(this));
        }
        this.phones = phones;
    }

    public User phones(Set<Phone> phones) {
        this.setPhones(phones);
        return this;
    }

    public User addPhone(Phone phone) {
        this.phones.add(phone);
        phone.setUser(this);
        return this;
    }

    public User removePhone(Phone phone) {
        this.phones.remove(phone);
        phone.setUser(null);
        return this;
    }

    public static UserDTO toDto(User user) {
        return UserDTO.builder().build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder().build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return getId() != null && getId().equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserE{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}

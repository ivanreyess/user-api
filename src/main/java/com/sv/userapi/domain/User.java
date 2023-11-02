package com.sv.userapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.domain.validation.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email")
    private String email;

    @Column(name = "password")
    @Password
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "token")
    private String token;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "last_login", nullable = false)
    @LastModifiedDate
    private long lastLogin;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
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
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .isActive(user.getActive())
                .phones(user.getPhones().stream().map(Phone::toDto).collect(Collectors.toSet()))
                .token(user.getToken())
                .lastLogin(user.getLastLogin())
                .createdDate(user.createdDate)
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.id())
                .email(userDTO.email())
                .password(userDTO.password())
                .name(userDTO.name())
                .active(userDTO.isActive())
                .phones(userDTO.phones().stream().map(Phone::toEntity).collect(Collectors.toSet()))
                .token(userDTO.token())
                .lastLogin(userDTO.lastLogin())
                .build();
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
        return "User {" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}

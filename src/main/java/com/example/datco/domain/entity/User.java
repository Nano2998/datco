package com.example.datco.domain.entity;

import com.example.datco.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends FullAuditEntity{

    private String name;

    private String email;

    private String contactInfo;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

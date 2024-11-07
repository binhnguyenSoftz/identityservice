package com.softz.identity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "invalidated_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {
    @Id
    String id;

    @Column(name = "expiry_time")
    Instant expiryTime;
}

package org.example.entities.ticket;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entities.client.Client;
import org.example.entities.planet.Planet;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    Client clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_planet_id", nullable = false)
    Planet fromPlanetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_planet_id", nullable = false)
    Planet toPlanetId;
}


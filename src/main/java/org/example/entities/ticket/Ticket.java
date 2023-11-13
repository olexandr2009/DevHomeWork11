package org.example.entities.ticket;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entities.client.Client;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "created_at")
    LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    Client client_id;

    @Column(name = "from_planet_id", nullable = false, length = 10)
    String from_planet_id;

    @Column(name = "to_planet_id", nullable = false, length = 10)
    String to_planet_id;
}


package org.example.entities.planet;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "planet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Planet {
    @Id
    @Column(name = "id", length = 10)
    String id;

    @Column(name = "name", length = 500)
    String name;
}

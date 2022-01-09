package com.works.metrostation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Metro{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = true)
    private Integer numberOfSeats;

    @Column(nullable = true)
    private Integer numberOfDoors;

    @Column(nullable = true)
    private Integer capacity;

}

package com.works.metrostation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "tr-TR", timezone = "Europe/Istanbul")
    @Column(nullable = false)
    private Date dateArrival;

    @ManyToOne
    @JoinColumn(name = "metro_id")
    private Metro metro;

}

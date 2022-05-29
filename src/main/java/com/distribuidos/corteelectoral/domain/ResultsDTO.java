package com.distribuidos.corteelectoral.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "resultsdb")
public class ResultsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name= "partido")
    private String nombre_partido;
    @Column(name="cantidad_votos")
    private Integer cantidadVotos;
}

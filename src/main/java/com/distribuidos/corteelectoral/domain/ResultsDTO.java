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
    private String partido_Lista;
    @Column(name= "partido")
    private String nombre_partido;
    @Column(name = "lista")
    private String nombre_lista;
    @Column(name="cantidad_votos")
    private Integer cantidadVotos;
}

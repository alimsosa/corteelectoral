package com.distribuidos.corteelectoral.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResultsDTO {
    private String nombre_partido;
    private Integer cantidadVotos;
}

package com.distribuidos.corteelectoral.repositories;

import com.distribuidos.corteelectoral.domain.ResultsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResultsRepository extends JpaRepository<ResultsDTO, Long> {
}

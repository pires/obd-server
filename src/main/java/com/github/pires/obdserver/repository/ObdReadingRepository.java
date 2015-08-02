package com.github.pires.obdserver.repository;

import com.github.pires.obdserver.model.ObdReading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * DAO for {@link ObdReading}.
 */
public interface ObdReadingRepository extends PagingAndSortingRepository<ObdReading, Long> {

    Page<ObdReading> findAllByVin(String vin, Pageable pageable);

}

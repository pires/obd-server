package com.github.pires.obdserver.service;

import com.github.pires.obdserver.model.ObdReading;
import com.github.pires.obdserver.repository.ObdReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReadingService {

    @Autowired
    private ObdReadingRepository repo;

    public ObdReading createReading(ObdReading reading) {
        return repo.save(reading);
    }

    public ObdReading getReading(long id) {
        return repo.findOne(id);
    }

    public Page<ObdReading> getAllReadingsFromVin(String vin, Integer page, Integer size) {
        return repo.findAllByVin(vin, new PageRequest(page, size));
    }

}

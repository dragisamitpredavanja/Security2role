package com.sbs.ald.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.OsobaRepository;

@Service
public class OsobaService {

    @Autowired
    private OsobaRepository osobaRepository;

    public boolean deleteOsobaById(Long id) {
        // Proveri da li osoba postoji
        if (osobaRepository.existsById(id)) {
            osobaRepository.deleteById(id);
            return true;
        }
        return false;
    }
   
    public Osoba saveOsobe(Osoba osoba) {
        return osobaRepository.save(osoba);
    }
}


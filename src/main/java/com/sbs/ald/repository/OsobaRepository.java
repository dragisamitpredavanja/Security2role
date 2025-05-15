package com.sbs.ald.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbs.ald.entitety.Osoba;

@Repository
public interface OsobaRepository extends JpaRepository<Osoba, Long> {
	@Query("SELECT o FROM Osoba o ORDER BY o.brojLajkova DESC")
	 List<Osoba> findAll();
    List<Osoba> findByGrad(String grad);

    Optional<Osoba> findByImeAndPrezime(String ime, String prezime);

    List<Osoba> findByBrojLajkovaGreaterThan(int brojLajkova);

    List<Osoba> findTop5ByOrderByBrojLajkovaDesc();

    // Filtriranje po minimalnoj lepoti
    List<Osoba> findByLepotaGreaterThanEqual(int minLepota);

    // Filtriranje po minimalnoj pameti
    List<Osoba> findByPametGreaterThanEqual(int minPamet);

    // Filtriranje po minimalnoj visini
    List<Osoba> findByVisinaGreaterThanEqual(int minVisina);

    // Kombinovano: min lepota, pamet, visina
    List<Osoba> findByLepotaGreaterThanEqualAndPametGreaterThanEqualAndVisinaGreaterThanEqual(
        int minLepota, int minPamet, int minVisina);
}


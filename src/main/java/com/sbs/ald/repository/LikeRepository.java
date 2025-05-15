package com.sbs.ald.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.ald.dto.User;
import com.sbs.ald.entitety.Like;
import com.sbs.ald.entitety.Osoba;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Pronalaženje lajka na osnovu korisnika i osobe
//    Like findByUserAndOsoba(User user, Osoba osoba);
    Like findByUserAndOsoba(User user, Osoba osoba);
    int countByOsobaAndIsLikedTrue(Osoba osoba);


}


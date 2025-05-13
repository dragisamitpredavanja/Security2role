package com.sbs.ald.repository;



import com.sbs.ald.entitety.Like;
import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Pronalaženje lajka na osnovu korisnika i osobe
    Like findByUserAndOsoba(User user, Osoba osoba);
}


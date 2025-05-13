package com.sbs.ald.entitety;

import com.sbs.ald.dto.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Komentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tekst;

    @ManyToOne
    private Osoba osoba;

    @ManyToOne
    private User user;

    // Bez-parametarski konstruktor (potreban za JPA/Hibernate)
    public Komentar() {
    }

    // Parametarski konstruktor (ako ti je potreban)
    public Komentar(String tekst, Osoba osoba, User user) {
        this.tekst = tekst;
        this.osoba = osoba;
        this.user = user;
    }

    // Getter i Setter metode
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

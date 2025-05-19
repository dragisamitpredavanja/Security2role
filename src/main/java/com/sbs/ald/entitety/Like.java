package com.sbs.ald.entitety;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbs.ald.dto.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes") 
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Korisnik koji lajkuje

    @ManyToOne
    @JoinColumn(name = "osoba_id")
    @JsonIgnore
    private Osoba osoba;  // Osoba koja je lajkovana

    private boolean isLiked;  // True = lajkovano, False = skinut like

	public Like() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Like(Long id, User user, Osoba osoba, boolean isLiked) {
		super();
		this.id = id;
		this.user = user;
		this.osoba = osoba;
		this.isLiked = isLiked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

    
}


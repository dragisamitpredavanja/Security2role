package com.sbs.ald.entitety;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "osobe")
public class Osoba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String grad;
    @Lob
    private byte[] slika;

    public byte[] getSlika() {
		return slika;
	}



	public void setSlika(byte[] slika) {
		this.slika = slika;
	}

	// Ove vrednosti sada predstavljaju brojčanu ocenu (npr. 1–5)
    private int lepota;     // 1 do 5
    private int pamet;      // 1 do 5
    private int visina;     // 1 do 5

    private int brojLajkova;

    @OneToMany(mappedBy = "osoba", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Komentar> komentari = new ArrayList<>();

    // Veza sa Like entitetom, kako bi pratili lajkovanje
    @OneToMany(mappedBy = "osoba", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>(); // Lista lajkovanja na osobu

    public Osoba() {
        super();
    }

  

    public Osoba(Long id, String ime, String prezime, String grad, byte[] slika, int lepota, int pamet, int visina,
			int brojLajkova, List<Komentar> komentari, List<Like> likes) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.slika = slika;
		this.lepota = lepota;
		this.pamet = pamet;
		this.visina = visina;
		this.brojLajkova = brojLajkova;
		this.komentari = komentari;
		this.likes = likes;
	}



	// Getter i setter metode
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

  

    



	



	public int getLepota() {
        return lepota;
    }

    public void setLepota(int lepota) {
        this.lepota = lepota;
    }

    public int getPamet() {
        return pamet;
    }

    public void setPamet(int pamet) {
        this.pamet = pamet;
    }

    public int getVisina() {
        return visina;
    }

    public void setVisina(int visina) {
        this.visina = visina;
    }

    public int getBrojLajkova() {
        return brojLajkova;
    }

    public void setBrojLajkova(int brojLajkova) {
        this.brojLajkova = brojLajkova;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }
}

package com.sbs.ald.dto;

public class OsobaDtoSlikaBaza {
    
    private String ime;
    private String prezime;
    private String grad;
    private int lepota;
    private int pamet;
    private int visina;
    private int brojLajkova;
    private byte[] slika; // Ovo je polje za sliku u formatu byte[]

    // Getteri i setteri
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

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    // Konstruktor
    public OsobaDtoSlikaBaza() {
        super();
    }

    // Konstruktor sa parametrima
    public OsobaDtoSlikaBaza(String ime, String prezime, String grad, int lepota, int pamet, int visina,
            int brojLajkova, byte[] slika) {
        super();
        this.ime = ime;
        this.prezime = prezime;
        this.grad = grad;
        this.lepota = lepota;
        this.pamet = pamet;
        this.visina = visina;
        this.brojLajkova = brojLajkova;
        this.slika = slika;
    }
}

package com.sbs.ald.dto;

public class OsobaDTO {
	private Long id;
    public String ime;
    public String prezime;
    public String grad;
    public byte[] slika;
    public int lepota;
    public int pamet;
    public int visina;
    private int brojLajkova;
	public byte[] getSlika() {
		return slika;
	}
	public void setSlika(byte[] slika) {
		this.slika = slika;
	}
	public int getBrojLajkova() {
		return brojLajkova;
	}
	public void setBrojLajkova(int brojLajkova) {
		this.brojLajkova = brojLajkova;
	}
	public OsobaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OsobaDTO(Long id, String ime, String prezime, String grad, byte[] slika, int lepota, int pamet, int visina,
			int brojLajkova) {
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
	}
	
	public OsobaDTO(Long id, String ime, String prezime, String grad, int lepota, int pamet, int visina,
			int brojLajkova) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.lepota = lepota;
		this.pamet = pamet;
		this.visina = visina;
		this.brojLajkova = brojLajkova;
	}
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
    
}

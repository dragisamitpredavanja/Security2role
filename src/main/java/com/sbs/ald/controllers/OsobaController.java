package com.sbs.ald.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbs.ald.dto.OsobaDtoSlikaBaza;
import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.OsobaRepository;
import com.sbs.ald.service.OsobaService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/osobe")
public class OsobaController {

	 private final OsobaRepository osobaRepository;
	 @Autowired
	    private OsobaService osobaService;
	    public OsobaController(OsobaRepository osobaRepository) {
	        this.osobaRepository = osobaRepository;
	    }
	    private final String UPLOAD_DIR = "C:/radni/MLD/Security2role/uploads/";
//	    @PostMapping("/createOsoba")
//	    @CrossOrigin
//	    public Osoba createOsoba(@RequestBody Osoba osoba) {
//	        return osobaRepository.save(osoba);
//	    }


//	        private final String UPLOAD_DIR = "uploads/"; // folder se nalazi u root-u projekta
//	    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
//
//
//	        @PostMapping("/createOsoba")
//	        @CrossOrigin
//	        public ResponseEntity<Osoba> createOsoba(
//	                @RequestParam String ime,
//	                @RequestParam String prezime,
//	                @RequestParam String grad,
//	                @RequestParam int lepota,
//	                @RequestParam int pamet,
//	                @RequestParam int visina,
//	                @RequestParam("slika") MultipartFile slikaFile
//	        ) {
//	            try {
//	                // Sačuvaj fajl
//	                String fileName = UUID.randomUUID() + "_" + slikaFile.getOriginalFilename();
//	                Path filePath = Paths.get(UPLOAD_DIR + fileName);
//	                Files.createDirectories(filePath.getParent());
//	                slikaFile.transferTo(filePath.toFile());
//
//	                // Napravi osobu i setuj putanju slike
//	                Osoba osoba = new Osoba();
//	                osoba.setIme(ime);
//	                osoba.setPrezime(prezime);
//	                osoba.setGrad(grad);
//	                osoba.setLepota(lepota);
//	                osoba.setPamet(pamet);
//	                osoba.setVisina(visina);
//	                osoba.setBrojLajkova(0);
//	                osoba.setSlikaUrl("/uploads/" + fileName); // Ovo frontend koristi za prikaz
//
//	                // Sačuvaj u bazu
//	                Osoba saved = osobaService.saveOsobe(osoba);
//	                return ResponseEntity.ok(saved);
//
//	            } catch (IOException e) {
//	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	            }
//	        }
//	   
//	    @PostMapping("/createOsoba")
//	    public ResponseEntity<Osoba> createOsoba(
//	            @RequestParam String ime,
//	            @RequestParam String prezime,
//	            @RequestParam String grad,
//	            @RequestParam int lepota,
//	            @RequestParam int pamet,
//	            @RequestParam int visina,
//	            @RequestParam("slika") MultipartFile slika,
//	            @RequestParam int brojLajkova
//	    ) throws IOException {
//	        Osoba osoba = new Osoba();
//	        osoba.setIme(ime);
//	        osoba.setPrezime(prezime);
//	        osoba.setGrad(grad);
//	        osoba.setLepota(lepota);
//	        osoba.setPamet(pamet);
//	        osoba.setVisina(visina);
//	        osoba.setBrojLajkova(brojLajkova);
//	        osoba.setSlika(slika.getBytes()); // slika kao byte[]
//
//	        Osoba saved = osobaService.saveOsobe(osoba);
//	        return ResponseEntity.ok(saved);
//	    }
	    @CrossOrigin
	    @PostMapping("/createOsoba")
	    public ResponseEntity<Osoba> createOsoba(
	            @RequestParam String ime,
	            @RequestParam String prezime,
	            @RequestParam String grad,
	            @RequestParam int lepota,
	            @RequestParam int pamet,
	            @RequestParam int visina,
	            @RequestParam("slika") MultipartFile slika,
	            @RequestParam int brojLajkova
	    ) throws IOException {
	        byte[] slikaBytes = slika.getBytes();

	        Osoba osoba = new Osoba();
	        osoba.setIme(ime);
	        osoba.setPrezime(prezime);
	        osoba.setGrad(grad);
	        osoba.setLepota(lepota);
	        osoba.setPamet(pamet);
	        osoba.setVisina(visina);
	        osoba.setBrojLajkova(brojLajkova);
	        osoba.setSlika(slikaBytes);

	        Osoba saved = osobaRepository.save(osoba);
	        return ResponseEntity.ok(saved);
	    }


	    @CrossOrigin
	    @PostMapping("/createOsobaPrva")
	    public ResponseEntity<Osoba> createOsoba(@ModelAttribute OsobaDtoSlikaBaza osobaDto) throws IOException {
	        // 1. Čitanje slike kao byte[] iz MultipartFile
			byte[] slikaBytes = osobaDto.getSlika();

			// 2. Kreiranje entiteta
			Osoba osoba = new Osoba();
			osoba.setIme(osobaDto.getIme());
			osoba.setPrezime(osobaDto.getPrezime());
			osoba.setGrad(osobaDto.getGrad());
			osoba.setLepota(osobaDto.getLepota());
			osoba.setPamet(osobaDto.getPamet());
			osoba.setVisina(osobaDto.getVisina());
			osoba.setBrojLajkova(osobaDto.getBrojLajkova());
			osoba.setSlika(slikaBytes); // Čuvanje slike u bazi

			// 3. Čuvanje osobe u bazi
			Osoba sacuvana = osobaRepository.save(osoba);

			return ResponseEntity.ok(sacuvana);
	    }
	    @CrossOrigin
	    @GetMapping("/getSlika/{id}")
	    public ResponseEntity<String> getSlika(@PathVariable Long id) {
	        Osoba osoba = osobaRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Osoba nije pronađena"));

	        // Konvertuj sliku iz byte[] u base64
	        String encodedImage = Base64.getEncoder().encodeToString(osoba.getSlika());

	        return ResponseEntity.ok(encodedImage);
	    }

//	    @CrossOrigin(origins = "http://localhost:3000")
	    @GetMapping("/test")
	    @CrossOrigin(origins = "http://localhost:3000")
	    public String test() {
	        
	        return "test radi";
	    }
	    @CrossOrigin(origins = "http://localhost:3000")
	    @GetMapping("/{id}")
	    public ResponseEntity<Osoba> getOsoba(@PathVariable Long id) {
	        Osoba osoba = osobaRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Osoba nije pronađena"));
	        return ResponseEntity.ok(osoba);
	    }
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteOsoba(@PathVariable Long id) {
	        boolean isDeleted = osobaService.deleteOsobaById(id);
	        if (isDeleted) {
	            return ResponseEntity.ok("Osoba uspešno obrisana");
	        } else {
	            return ResponseEntity.status(404).body("Osoba sa datim ID-jem nije pronađena");
	        }
	    }

    // Primer GET zahteva: /api/osobe/filter?lepota=3&pamet=4&visina=2
    @GetMapping("/filter")
    @CrossOrigin
    public List<Osoba> filterOsobe(
            @RequestParam(defaultValue = "0") int lepota,
            @RequestParam(defaultValue = "0") int pamet,
            @RequestParam(defaultValue = "0") int visina) {

        return osobaRepository.findByLepotaGreaterThanEqualAndPametGreaterThanEqualAndVisinaGreaterThanEqual(
                lepota, pamet, visina
        );
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<Osoba> getAllOsobe() {
        return osobaRepository.findAll();
}

}
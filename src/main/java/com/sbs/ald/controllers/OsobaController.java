package com.sbs.ald.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sbs.ald.dto.OsobaDtoSlikaBaza;
import com.sbs.ald.dto.User;
import com.sbs.ald.entitety.Like;
import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.LikeRepository;
import com.sbs.ald.repository.OsobaRepository;
import com.sbs.ald.repository.UserRepository;
import com.sbs.ald.service.OsobaService;

import io.jsonwebtoken.Jwt;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/osobe")
public class OsobaController {
	  @Autowired
	    private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private UserRepository userRepository;

	 private final OsobaRepository osobaRepository;
	 @Autowired
	    private OsobaService osobaService;
	    public OsobaController(OsobaRepository osobaRepository) {
	        this.osobaRepository = osobaRepository;
	    }
	    private final String UPLOAD_DIR = "C:/radni/MLD/Security2role/uploads/";

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
//	    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")

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
	  
	    public ResponseEntity<Osoba> getOsoba(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
//	        String username = jwt.getClaimAsString("sub");
	        Osoba osoba = osobaRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Osoba nije pronađena"));
	        return ResponseEntity.ok(osoba);
	    }
//	    @CrossOrigin(origins = "http://localhost:3000")
//	    @GetMapping("/{id}")
//	    public ResponseEntity<Osoba> getOsoba(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
//	        String username = jwt.getClaimAsString("preferred_username");
//	        // koristi username da vidiš da li korisnik ima pravo na osobu
//	        return ResponseEntity.ok(osobaRepository.findById(id));
//	    }
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{id}/like")
    public ResponseEntity<Osoba> toggleLike(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Osoba osoba = osobaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Osoba not found"));
        messagingTemplate.convertAndSend("/topic/likes", osoba);
        Boolean liked = body.get("liked");
        if (liked == null) return ResponseEntity.badRequest().build();

        Optional<Like> existingLikeOpt = likeRepository.findByUserAndOsoba(user, osoba);

        if (existingLikeOpt.isPresent()) {
            Like existingLike = existingLikeOpt.get();
            if (existingLike.isLiked() != liked) {
                // Ažuriraj status lajka
                existingLike.setLiked(liked);
                likeRepository.save(existingLike);

                // Ažuriraj broj lajkova
                int adjustment = liked ? 1 : -1;
                osoba.setBrojLajkova(Math.max(0, osoba.getBrojLajkova() + adjustment));
                osobaRepository.save(osoba);
            }
        } else if (liked) {
            // Ako nije lajkovao ranije, i sad lajkuje
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setOsoba(osoba);
            newLike.setLiked(true);
            likeRepository.save(newLike);

            osoba.setBrojLajkova(osoba.getBrojLajkova() + 1);
            osobaRepository.save(osoba);
        }
        // 🔴 Ova linija šalje ažuriranu osobu preko WebSocketa
        messagingTemplate.convertAndSend("/topic/likes", osoba);
        return ResponseEntity.ok(osoba);
    }
    // DTO klasa za request telo
    public static class LikeRequest {
        private boolean liked;

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }
    }

}
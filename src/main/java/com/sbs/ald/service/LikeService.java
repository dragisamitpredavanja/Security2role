package com.sbs.ald.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sbs.ald.dto.User;
import com.sbs.ald.entitety.Like;
import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.LikeRepository;
import com.sbs.ald.repository.OsobaRepository;
import com.sbs.ald.repository.UserRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private OsobaRepository osobaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // WebSocket slanje

    public void toggleLike(Long userId, Long osobaId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Osoba osoba = osobaRepository.findById(osobaId).orElseThrow(() -> new RuntimeException("Osoba not found"));

        Like existingLike = likeRepository.findByUserAndOsoba(user, osoba);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
            osoba.setBrojLajkova(osoba.getBrojLajkova() - 1);
        } else {
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setOsoba(osoba);
            newLike.setLiked(true);
            likeRepository.save(newLike);
            osoba.setBrojLajkova(osoba.getBrojLajkova() + 1);
        }

        osobaRepository.save(osoba);

        // WebSocket emitovanje ažuriranog broja lajkova
        messagingTemplate.convertAndSend(
            "/topic/like-updates/" + osoba.getId(),
            osoba.getBrojLajkova()
        );
    }

    public int getLikeCount(Long osobaId) {
        Osoba osoba = osobaRepository.findById(osobaId).orElseThrow(() -> new RuntimeException("Osoba not found"));
        return osoba.getBrojLajkova();
    }
}

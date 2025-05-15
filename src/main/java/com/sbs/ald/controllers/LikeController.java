package com.sbs.ald.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.OsobaRepository;
import com.sbs.ald.service.LikeService;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private OsobaRepository osobaRepository;

    @PostMapping("/toggle")
    public void toggleLike(@RequestParam Long userId, @RequestParam Long osobaId) {
        likeService.toggleLike(userId, osobaId);
    }

    @GetMapping("/count/{osobaId}")
    public int getLikeCount(Long osobaId) {
        Osoba osoba = osobaRepository.findById(osobaId)
            .orElseThrow(() -> new RuntimeException("Osoba not found"));
        return osoba.getBrojLajkova();
    }

}


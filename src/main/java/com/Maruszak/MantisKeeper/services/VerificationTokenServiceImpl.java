package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.model.VerificationToken;
import com.Maruszak.MantisKeeper.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public void save(VerificationToken verificationToken) {
        tokenRepository.save(verificationToken);
    }

    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteTokenById(UUID uuid){
        tokenRepository.deleteById(uuid);
    }

    public void deleteAllByUser(User user) {
        tokenRepository.deleteAllByUser(user);
    }
}


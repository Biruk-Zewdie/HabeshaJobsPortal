package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.VerificationTokenDAO;
import com.biruk.habeshaJobs.Model.User.User;
import com.biruk.habeshaJobs.Model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private final VerificationTokenDAO verificationTokenDAO;
    private static final long tokenExpirationTime = 30 * 60;     //token

    @Autowired
    public VerificationTokenService(VerificationTokenDAO verificationTokenDAO){
        this.verificationTokenDAO = verificationTokenDAO;
    }

    public VerificationToken createVerificationToken (User user) {

        byte [] byteArray = new byte[32];
        new SecureRandom().nextBytes(byteArray);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(byteArray);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpiredAt(LocalDateTime.now().plusSeconds(tokenExpirationTime));
        verificationToken.setUser(user);

        return verificationTokenDAO.save(verificationToken);
    }

    public VerificationToken findById (UUID tokenId) {

        return verificationTokenDAO.findById(tokenId).orElseThrow(() ->
            new NoSuchElementException("The token with token Id: " + tokenId + " not found"));
    }

    public  void deleteVerificationToken (UUID tokenId) {

        if(!verificationTokenDAO.existsById(tokenId)){
            throw new NoSuchElementException("The token with token Id: " + tokenId + " not found");
        }
        verificationTokenDAO.deleteById(tokenId);
    }
}

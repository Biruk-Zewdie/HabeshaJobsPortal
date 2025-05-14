package com.biruk.habeshaJobs.Model;

import com.biruk.habeshaJobs.Model.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID tokenId;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    /* why secure token has a many to one relationship with user?
       because a user can have multiple secure tokens in a situation where
        1. A user might request email verification multiple times (e.g, if the first token expires).
        2. Multiple tokens might exist with different TimeStamps.
        3. you might want to invalidate older tokens or log past activity.
    */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private boolean isExpired;

    public VerificationToken() {
    }

    public VerificationToken(UUID tokenId, String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user, boolean isExpired) {
        this.tokenId = tokenId;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
        this.isExpired = isExpired;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "tokenId=" + tokenId +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiredAt +
                ", user=" + user +
                ", isExpired=" + isExpired +
                '}';
    }
}

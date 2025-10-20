package com.tobyspring.splearn.domain;

import org.springframework.util.Assert;

import java.util.Objects;

import static org.springframework.util.Assert.*;

public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public Member(String email, String nickname, String passwordHash) {
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);

        this.status = MemberStatus.PENDING;
    }


    public void activate() {
        state(this.status == MemberStatus.PENDING, "Pending 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;


    }
}

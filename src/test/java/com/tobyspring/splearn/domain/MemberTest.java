package com.tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tobyspring.splearn.domain.MemberStatus.ACTIVE;
import static com.tobyspring.splearn.domain.MemberStatus.DEACTIVATED;
import static com.tobyspring.splearn.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        member = Member.create(new MemberCreateRequest("toby@splearn.app", "toby", "secret"), passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void activate() {
        member.activate();
        assertThat(member.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();
        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);

    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("toby");

        member.changeNickname("Charlie");

        assertThat(member.getNickname()).isEqualTo("Charlie");
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret", passwordEncoder);

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void shouldBeActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> Member.create(new MemberCreateRequest("invalid email", "toby", "secret"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("valid@email.com", "toby", "secret"), passwordEncoder);
    }
}
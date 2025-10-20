package com.tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static com.tobyspring.splearn.domain.MemberStatus.ACTIVE;
import static com.tobyspring.splearn.domain.MemberStatus.DEACTIVATED;
import static com.tobyspring.splearn.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("toby@splearn.app", "toby", "secret");
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "toby", "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("toby", "Toby", "secret");

        member.activate();
        assertThat(member.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("toby", "Toby", "secret");

        member.activate();
        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);

    }

    @Test
    void deactivate() {
        var member = new Member("toby", "Toby", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("toby", "Toby", "secret");

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
}
package com.tobyspring.splearn.application.provided;

import com.tobyspring.splearn.SplearnTestConfiguration;
import com.tobyspring.splearn.domain.DuplicateEmailException;
import com.tobyspring.splearn.domain.Member;
import com.tobyspring.splearn.domain.MemberFixture;
import com.tobyspring.splearn.domain.MemberRegisterRequest;
import com.tobyspring.splearn.domain.MemberStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("toby@splearn.app", "Toby", "longsecret"));
        extracted(new MemberRegisterRequest("toby@splearn.app", "Charlie______________________", "secret"));
        extracted(new MemberRegisterRequest("tobysplearn.app", "Charlie______________________", "secret"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
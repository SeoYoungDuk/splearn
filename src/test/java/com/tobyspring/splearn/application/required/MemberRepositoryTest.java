package com.tobyspring.splearn.application.required;

import com.tobyspring.splearn.domain.Member;
import com.tobyspring.splearn.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void registerMember() {
        Member member = memberRepository.save(Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder()));

        entityManager.flush();
    }
}
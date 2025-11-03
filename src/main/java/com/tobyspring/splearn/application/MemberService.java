package com.tobyspring.splearn.application;

import com.sun.jdi.request.DuplicateRequestException;
import com.tobyspring.splearn.application.provided.MemberRegister;
import com.tobyspring.splearn.application.required.EmailSender;
import com.tobyspring.splearn.application.required.MemberRepository;
import com.tobyspring.splearn.domain.DuplicateEmailException;
import com.tobyspring.splearn.domain.Email;
import com.tobyspring.splearn.domain.Member;
import com.tobyspring.splearn.domain.MemberRegisterRequest;
import com.tobyspring.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        //check
        checkDuplicateEmail(registerRequest);

        //domain model
        Member member = Member.register(registerRequest, passwordEncoder);

        //repository
        memberRepository.save(member);

        //post process
        sendWelcomeEmail(member);

        return member;
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일 입니다. : " + registerRequest.email());
        }
    }
}

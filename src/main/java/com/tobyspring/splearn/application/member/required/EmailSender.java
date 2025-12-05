package com.tobyspring.splearn.application.member.required;

import com.tobyspring.splearn.domain.shared.Email;

/**
 * 이메일을 발송된다
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}

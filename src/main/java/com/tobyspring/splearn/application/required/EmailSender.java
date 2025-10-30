package com.tobyspring.splearn.application.required;

import com.tobyspring.splearn.domain.Email;

/**
 * 이메일을 발송된다
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}

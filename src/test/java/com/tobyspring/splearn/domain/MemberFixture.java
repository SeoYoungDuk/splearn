package com.tobyspring.splearn.domain;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest("toby@splearn.app", "toby", "secret");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}

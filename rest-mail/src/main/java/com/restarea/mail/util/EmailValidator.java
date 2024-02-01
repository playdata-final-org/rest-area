package com.restarea.mail.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email = (String) target;
        if (!isValidEmail(email)) {
            errors.rejectValue("recipient", "email.invalid", "유효하지 않은 이메일 주소입니다.");
        }
    }

    private boolean isValidEmail(String email) {
        // 간단한 이메일 유효성 검사 로직을 작성합니다.
        // 여기에서는 정규식을 사용하여 이메일 형식을 확인합니다.
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
}
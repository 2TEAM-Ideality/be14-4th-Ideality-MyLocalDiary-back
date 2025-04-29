package com.leesang.mylocaldiary.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendServiceImpl implements EmailSendService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSendServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailByVerification(String email, String verificationCode) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlMsg = "<div style='font-family:Arial,sans-serif;'>"
                    + "<h2>My Local Diary 이메일 인증</h2>"
                    + "<p>아래 인증번호를 입력해주세요:</p>"
                    + "<p style='color:blue;'>" + verificationCode + "</p>"
                    + "<p>감사합니다!</p>"
                    + "</div>";

            helper.setFrom("My Local Diary <mylocaldiary@gmail.com>");
            helper.setTo(email);
            helper.setSubject("My Local Diary - 이메일 인증번호");
            helper.setText(htmlMsg, true); // true: HTML 사용

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

}


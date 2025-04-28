package com.leesang.mylocaldiary.email.service;

public interface EmailSendService {
    void sendEmailByVerification(String email, String verificationCode);
}

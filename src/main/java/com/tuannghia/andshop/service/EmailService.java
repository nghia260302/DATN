package com.tuannghia.andshop.service;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}

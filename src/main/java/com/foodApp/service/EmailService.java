package com.foodApp.service;

import com.foodApp.dto.request.EmailRequest;

public interface EmailService {
    String sendSimpleMail(EmailRequest emailRequest);

    String sendMailWithAttachment(EmailRequest request);
}

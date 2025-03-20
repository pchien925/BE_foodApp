package com.foodApp.service;

import com.foodApp.dto.request.EmailRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface EmailService {
    CompletableFuture<String> sendSimpleMail(EmailRequest emailRequest);

    String sendMailWithAttachment(EmailRequest request);
}

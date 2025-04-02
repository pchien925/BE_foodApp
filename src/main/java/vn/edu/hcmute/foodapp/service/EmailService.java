package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.EmailRequest;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    String sendSimpleMail(EmailRequest emailRequest);

    String sendMailWithAttachment(EmailRequest request);
}

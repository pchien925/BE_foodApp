package com.foodApp.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}

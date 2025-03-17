package ru.Sber.SberDiplomaPaper.service.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationEmailDetails implements EmailDetails {
    private String from;
    private String to;
    private String serverURL;
    private String token;


}

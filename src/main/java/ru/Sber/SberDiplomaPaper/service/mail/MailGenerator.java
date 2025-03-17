package ru.Sber.SberDiplomaPaper.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface MailGenerator {
    MimeMessage generateMailMessage(MimeMessage mimeMessage, EmailDetails emailDetails) throws MessagingException;
}

package ru.Sber.SberDiplomaPaper.service.mail;


import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender javaMailSender;
    private final ApplicationContext applicationContext;

    private Map<Class<?>, MailGenerator> mailGeneratorMap;

    @PostConstruct
    public void init() {
        mailGeneratorMap = new HashMap<>();
        mailGeneratorMap.put(RegistrationEmailDetails.class, applicationContext.getBean(RegistrationMailGenerator.class));
    }


    @Async
    @Override
    public void sendEmail(EmailDetails details) {
        try {
            MailGenerator generator = mailGeneratorMap.get(details.getClass());
            MimeMessage message = generator.generateMailMessage(javaMailSender.createMimeMessage(), details);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}

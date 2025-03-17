package ru.Sber.SberDiplomaPaper.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMailGenerator implements MailGenerator {

    private static String getHtml(RegistrationEmailDetails details) {
        String htmlFormat = """
                <html>
                    <h1>Registration</h1>
                    <p>For registration click this link</p>
                    <p>
                        <a href="%s">Click here</a>
                    </p>
                </html>""";
        String referenceFormat = "%s/api/auth/confirm?token=%s";
        String reference = referenceFormat.formatted(details.getServerURL(), details.getToken());
        return htmlFormat.formatted(reference);
    }

    @Override
    public MimeMessage generateMailMessage(MimeMessage mimeMessage, EmailDetails emailDetails) throws MessagingException {
        if (emailDetails instanceof RegistrationEmailDetails details) {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(details.getFrom());
            mimeMessageHelper.setTo(details.getTo());
            mimeMessageHelper.setSubject("Complete Registration");
            String html = getHtml(details);
            mimeMessageHelper.setText(html, true);
            return mimeMessageHelper.getMimeMessage();
        } else {
            throw new UnsupportedOperationException("EmailDetails is not RegistrationEmailDetails");
        }
    }
}

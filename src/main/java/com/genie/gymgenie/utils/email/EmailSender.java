package com.genie.gymgenie.utils.email;

import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.exception.EmailSenderException;
import com.genie.gymgenie.security.payload.ApiError;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@Component
@RequiredArgsConstructor
public class  EmailSender {

    private final JavaMailSender sender;
    private final GenieLogger genie = new GenieLogger(EmailSender.class);

    @Async
    public void send(String to, String email, String subject) {
        genie.info("Sending email");

        try{
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("parunev@gmail.com");

            genie.info("Email sent");
            sender.send(mimeMessage);

        } catch (MessagingException e) {
            genie.error("Failed to send email", e);

            throw new EmailSenderException(ApiError.builder()
                    .path(getCurrentRequest())
                    .error("Failed to send email. %s".formatted(e.getMessage()))
                    .timestamp(LocalDateTime.now())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
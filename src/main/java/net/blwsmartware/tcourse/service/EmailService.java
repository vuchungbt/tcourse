package net.blwsmartware.tcourse.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import net.blwsmartware.tcourse.dto.request.EmailRequest;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService   {

    JavaMailSender mailSender;
    SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    @NonFinal
    String sender;


    @Async
    public String sendEmail(EmailRequest emailRequest) {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
        log.info("Email sending...");

        Context context = new Context();
        context.setVariable("name",emailRequest.getName());
        context.setVariable("content",emailRequest.getContent());

        String html = templateEngine.process("welcome-email",context);

        try {
            helper.setTo(emailRequest.getTo() );
            helper.setText(html,true);
            helper.setSubject("Reset password");
            helper.setFrom(sender);

            mailSender.send(message);
            log.info("Email sent to {}... Done!",emailRequest.getTo());

            return  "Done";

        } catch (MessagingException e) {
            log.error( "MessagingException :{}", e.getMessage());
            throw new AppRuntimeException(ErrorResponse.EMAIL_INVALID);
        }

    }
}

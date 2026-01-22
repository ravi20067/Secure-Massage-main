package com.Security.Secure.Massage.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailGenerator {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom("ravi2006.7.developer@gmail.com");

            javaMailSender.send(mimeMessage);
            log.info("✅ Email sent successfully to: {}", to);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("❌ Error while sending email: ", e);
        }
    }
}

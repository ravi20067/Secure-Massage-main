package com.Security.Secure.Massage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmailService {

    @Autowired
    private EmailGenerator emailGenerator;

    @Autowired
    private LoadHtmlContent loadHtmlContent;

    public void sendOtpEmail(
            String email,
            int otp,
            String brandName,
            String expiryMinutes,
            String location,
            String device,
            String ipAddress,
            String supportEmail,
            String companyName,
            String companyAddress,
            String secureAccountUrl,
            String privacyPolicyUrl,
            String helpCenterUrl
    ) {
        String otpString = String.valueOf(otp);
        String requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String requestId = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        String htmlContent = loadHtmlContent.generateOtpEmail(
                brandName,
                otpString,
                expiryMinutes,
                requestTime,
                location,
                device,
                ipAddress,
                requestId,
                supportEmail,
                companyName,
                companyAddress,
                secureAccountUrl,
                privacyPolicyUrl,
                helpCenterUrl
        );

        String subject = "🔐 Your " + brandName + " OTP Code: " + otpString;
        emailGenerator.sendHtmlEmail(email, subject, htmlContent);

    }

}

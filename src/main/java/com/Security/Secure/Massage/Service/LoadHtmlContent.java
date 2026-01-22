package com.Security.Secure.Massage.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LoadHtmlContent {

    public String generateOtpEmail(
            String brandName,
            String otp,
            String expiryMinutes,
            String requestTime,
            String location,
            String device,
            String ipAddress,
            String requestId,
            String supportEmail,
            String companyName,
            String companyAddress,
            String secureAccountUrl,
            String privacyPolicyUrl,
            String helpCenterUrl
    ) {
        String html = loadContent("otp.html");

        Map<String, String> replacements = new HashMap<>();

        replacements.put("{{BRAND_NAME}}", brandName);
        replacements.put("{{OTP}}", otp);
        replacements.put("{{EXPIRY_MINUTES}}", expiryMinutes);
        replacements.put("{{DATE_TIME}}", requestTime);
        replacements.put("{{LOCATION}}", location);
        replacements.put("{{DEVICE_INFO}}", device);
        replacements.put("{{IP_ADDRESS}}", ipAddress);
        replacements.put("{{REQUEST_ID}}", requestId);
        replacements.put("{{SUPPORT_EMAIL}}", supportEmail);
        replacements.put("{{COMPANY_NAME}}", companyName);
        replacements.put("{{COMPANY_ADDRESS}}", companyAddress);
        replacements.put("{{SECURE_ACCOUNT_URL}}", secureAccountUrl);
        replacements.put("{{PRIVACY_POLICY_URL}}", privacyPolicyUrl);
        replacements.put("{{HELP_CENTER_URL}}", helpCenterUrl);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            html = html.replace(entry.getKey(), entry.getValue());
        }

        return html;
    }

    private String loadContent(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/EmailTemplate/" + fileName);
            InputStream is = resource.getInputStream();

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("❌ Error loading HTML file '{}': {}", fileName, e.getMessage());
            return "<p style='color:red;'>Error loading template: " + fileName + "</p>";
        }
    }
}

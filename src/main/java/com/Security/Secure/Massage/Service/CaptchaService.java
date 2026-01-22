package com.Security.Secure.Massage.Service;

import com.Security.Secure.Massage.util.CaptchaStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaStore captchaStore;

    // generate random text
    public String generateCaptchaText() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+{}";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // store captcha
    public String createCaptcha() {
        String captchaId = UUID.randomUUID().toString();
        String captchaText = generateCaptchaText();

        captchaStore.saveCaptcha(captchaId, captchaText);
        return captchaId;
    }

    // verify captcha
    public boolean verifyCaptcha(String captchaId, String userInput) {
        return captchaStore.verifyCaptcha(captchaId,userInput);
    }

    // get captcha text (for image rendering)
    public String getCaptchaText(String captchaId) {
        return captchaStore.getCaptcha(captchaId);
    }
}


package com.Security.Secure.Massage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CaptchaStore {
    private static final int CAPTCHA_TTL_MINUTES = 5;
    private static final String CAPTCHA_PREFIX = "CAPTCHA ";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveCaptcha(String captchaId, String captchaText) {
        String key = CAPTCHA_PREFIX + captchaId;

        redisTemplate.opsForValue().set(
                key,
                captchaText,
                Duration.ofMinutes(CAPTCHA_TTL_MINUTES)
        );
    }

    public String getCaptcha(String captchaId) {
        String key = CAPTCHA_PREFIX + captchaId;
        Object value = redisTemplate.opsForValue().get(key);

        return value != null ? value.toString() : null;
    }

    public boolean verifyCaptcha(String captchaId, String userInput) {
        String key = CAPTCHA_PREFIX + captchaId;

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return false;
        }

        redisTemplate.delete(key);

        return value.toString().equals(userInput);
    }

    public void removeCaptcha(String captchaId) {
        redisTemplate.delete(CAPTCHA_PREFIX + captchaId);
    }
}

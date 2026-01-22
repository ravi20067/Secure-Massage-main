package com.Security.Secure.Massage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private static final int OTP_TTL_MINUTES = 5;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String generateOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

    public void saveOtp(String email, String otp) {

        String key = "OTP:" + email;

        Map<String, Object> data =
                (Map<String, Object>) redisTemplate.opsForValue().get(key);

        if(data != null){
            throw new RuntimeException("Otp already sent");
        }

        String hashedOtp = passwordEncoder.encode(otp);

        Map<String, Object> value = new HashMap<>();
        value.put("otp", hashedOtp);
        value.put("attempts", 0);

        redisTemplate.opsForValue()
                .set(key, value, OTP_TTL_MINUTES, TimeUnit.MINUTES);
    }

    public boolean verifyOtp(String email, String inputOtp) {

        String key = "OTP:" + email;

        Map<String, Object> data =
                (Map<String, Object>) redisTemplate.opsForValue().get(key);

        if (data == null)
            throw new RuntimeException("OTP expired or not found");

        int attempts = (int) data.get("attempts");

        if (attempts >= 3) {
            redisTemplate.delete(key);
            throw new RuntimeException("Too many attempts");
        }

        String hashedOtp = (String) data.get("otp");

        if (!passwordEncoder.matches(inputOtp, hashedOtp)) {
            data.put("attempts", attempts + 1);
            redisTemplate.opsForValue().set(key, data);
            throw new RuntimeException("Invalid OTP");
        }

        redisTemplate.delete(key);
        return true;
    }
}


package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.User;
import com.Security.Secure.Massage.Enums.AuthType;
import com.Security.Secure.Massage.Service.CaptchaService;
import com.Security.Secure.Massage.Service.EmailService;
import com.Security.Secure.Massage.Service.OTPService;
import com.Security.Secure.Massage.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/register/perform_register")
    public String performRegister(
            @ModelAttribute("user") User user,
            Model model) {

        userService.addUser(user);

        return "redirect:/login";
    }

    @PostMapping("/register/send-otp")
    @ResponseBody
    public ResponseEntity<?> sendRegisterOtp(@RequestBody Map<String, String> req , HttpServletRequest request) {
        String captcha = req.get("captcha");
        String captchaId = req.get("captchaId");
        String email = req.get("email");
        if (!captchaService.verifyCaptcha(captchaId, captcha)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("❌ Invalid Captcha");
        }
        if (userService.findByEmail(email).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exist");
        }

        try{

            String otp = otpService.generateOtp();
            otpService.saveOtp(email,otp);
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }
            String device = request.getHeader("User-Agent");
            String location = "India";

            emailService.sendOtpEmail(email, Integer.parseInt(otp), "Secure Massage", "5", location, device, ipAddress, "support@securemassage.com", "Secure Massage Pvt Ltd", "123 Mango Lane, Nagpur, India ", "https://securemassage.com/secure", "https://securemassage.com/privacy", "https://securemassage.com/help");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/verify-otp")
    @ResponseBody
    public ResponseEntity<?> verifyRegisterOtp(
            @RequestBody Map<String, String> req) {

        String email = req.get("email");
        String otp = req.get("otp");

        try {
            boolean verified = otpService.verifyOtp(email, otp);

            if (!verified) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid OTP");
            }

            return ResponseEntity.ok("OTP verified");

        } catch (RuntimeException ex) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

}

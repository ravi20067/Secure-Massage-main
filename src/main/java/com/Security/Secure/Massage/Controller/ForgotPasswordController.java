package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Service.CaptchaService;
import com.Security.Secure.Massage.Service.EmailService;
import com.Security.Secure.Massage.Service.OTPService;
import com.Security.Secure.Massage.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/login/forgot-password")
    public String forgotpassword(Model model){
        model.addAttribute("currentPage", "home");
        return "forgot-password";
    }
    @PostMapping("/login/forgot-password/send-otp")
    public String handleOtpRequest(@RequestParam("email") String email, RedirectAttributes redirectAttributes , Model model, HttpServletRequest request) {
        String captcha = request.getParameter("captcha");
        String captchaId = request.getParameter("captchaId");

        if (!captchaService.verifyCaptcha(captchaId, captcha)) {
            return "redirect:/login/forgot-password?captchaError=true"; // 🔥 STOP login
        }
        if (userService.findByEmail(email).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found with this email!");
            return "redirect:/login/forgot-password";
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
            redirectAttributes.addFlashAttribute("msg", "OTP sent to " + email);
            return "redirect:/login/verify-otp-page?email=" + email;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login/forgot-password";
        }
    }
    @GetMapping("/login/verify-otp-page")
    public String showVerifyPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/login/forgot-password/verify")
    public String verify(
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes){

        try {
            boolean isValid = otpService.verifyOtp(email,otp);
            if (!isValid) {
                redirectAttributes.addFlashAttribute("error", "Invalid or expired OTP. Please try again.");
                return "redirect:/login/verify-otp-page?email=" + email;
            }
            userService.updatePassword(email, newPassword);
            return "redirect:/login?msg=Password reset successful! You can now login.";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login/verify-otp-page?email=" + email;
        }
    }

}

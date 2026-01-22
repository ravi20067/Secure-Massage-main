package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Service.CaptchaService;
import com.Security.Secure.Massage.util.CaptchaImageUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping
    public Map<String, String> generateCaptcha() {
        String captchaId = captchaService.createCaptcha();
        return Map.of(
                "captchaId", captchaId,
                "imageUrl", "/captcha/image/" + captchaId
        );
    }

    @GetMapping("/image/{captchaId}")
    public void captchaImage(@PathVariable String captchaId,
                             HttpServletResponse response) throws IOException {

        String text = captchaService.getCaptchaText(captchaId);

        if (text == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        BufferedImage image = CaptchaImageUtil.generateImage(text);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }
}


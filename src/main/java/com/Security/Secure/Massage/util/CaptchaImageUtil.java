package com.Security.Secure.Massage.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaImageUtil {

    public static BufferedImage generateImage(String text) {
        int width = 160;
        int height = 50;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // noise
        Random rand = new Random();
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 15; i++) {
            g.drawLine(rand.nextInt(width), rand.nextInt(height),
                    rand.nextInt(width), rand.nextInt(height));
        }

        // text
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.setColor(Color.BLACK);
        g.drawString(text, 30, 35);

        g.dispose();
        return image;
    }
}

package com.tutorialsejong.courseregistration.domain.auth.service;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.BackgroundProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import com.tutorialsejong.courseregistration.domain.auth.dto.CaptchaResult;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    private static final int CAPTCHA_WIDTH = 200;
    private static final int CAPTCHA_HEIGHT = 80;
    private static final int CAPTCHA_LENGTH = 4;

    private final WordRenderer wordRenderer;
    private final BackgroundProducer backgroundProducer;

    public CaptchaResult generateCaptcha() {
        Captcha captcha = new Captcha.Builder(CAPTCHA_WIDTH, CAPTCHA_HEIGHT)
                .addBackground(backgroundProducer)
                .addText(new NumbersAnswerProducer(CAPTCHA_LENGTH), wordRenderer)
                .build();

        String base64Image = convertToBase64(captcha.getImage());
        String imageUrl = "data:image/png;base64," + base64Image;

        return new CaptchaResult(captcha.getAnswer(), imageUrl);
    }

    private String convertToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert image to Base64", e);
        }
    }
}

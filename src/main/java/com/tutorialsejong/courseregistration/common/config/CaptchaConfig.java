package com.tutorialsejong.courseregistration.common.config;

import cn.apiclub.captcha.backgrounds.BackgroundProducer;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {

    @Bean
    public WordRenderer wordRenderer() {
        return new DefaultWordRenderer(
                List.of(new Color(0, 0, 0)),
                List.of(new Font("Helvetica", Font.PLAIN, 60))
        );
    }

    @Bean
    public BackgroundProducer backgroundProducer() {
        GradiatedBackgroundProducer producer = new GradiatedBackgroundProducer();
        producer.setFromColor(new Color(100, 100, 100));
        producer.setToColor(new Color(180, 180, 180));
        return producer;
    }
}

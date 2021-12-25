package person.crayon.controller;

import cn.hutool.core.img.ImgUtil;
import org.springframework.web.bind.annotation.*;
import person.crayon.domain.Captcha;
import person.crayon.service.CaptchaService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Crayon
 * @date 2021/12/25 22:19
 * @desc 验证码Contorller
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/get")
    public void get(
            HttpServletResponse response
    ) {
        try (
                ServletOutputStream os = response.getOutputStream()
        ) {
            Captcha captcha = captchaService.createCaptcha();
            response.addCookie(new Cookie("captcha", captcha.getKey()));
            ImgUtil.writeJpg(captcha.getImage(), os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/verify")
    public Map<String, Object> verify(
            HttpServletRequest request,
            @RequestParam String code
    ) {
        Cookie cookie = Arrays.stream(request.getCookies()).filter(e -> "captcha".equals(e.getName())).findFirst().orElseThrow(() -> new RuntimeException("cookie丢失"));
        System.out.println(cookie.getValue());
        System.out.println(code);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("data", captchaService.verify(cookie.getValue(), code));
        return res;
    }
}

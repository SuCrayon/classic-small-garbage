package person.crayon.domain;

import lombok.Data;

import java.awt.*;

/**
 * @author Crayon
 * @date 2021/12/25 22:34
 * @desc 验证码类
 */
@Data
public class Captcha {
    private String key;
    private String code;
    private Image image;
}

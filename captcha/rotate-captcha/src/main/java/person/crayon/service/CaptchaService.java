package person.crayon.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;
import person.crayon.domain.Captcha;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Crayon
 * @date 2021/12/25 22:21
 * @desc 验证码生成服务
 */
@Service
public class CaptchaService {

    private static final String[] IMAGES = {
            "static/images/captcha.jpeg"
    };
    private static final int WIDTH = 200;
    private static final int HEIGHT = WIDTH;
    private static final int TOLERANCE = 10;

    @Resource
    private CacheService cacheService;

    public Captcha createCaptcha() throws IOException {
        Captcha captcha = new Captcha();
        int deg = RandomUtil.randomInt(30, 180);
        captcha.setKey(IdUtil.fastSimpleUUID());
        captcha.setCode(String.valueOf(deg));
        captcha.setImage(createImage(-deg));
        cacheService.set(captcha.getKey(), captcha);
        return captcha;
    }

    private Image createImage(int deg) throws IOException {
        File file = FileUtil.file(IMAGES[RandomUtil.randomInt(IMAGES.length)]);
        Image image = ImgUtil.scale(ImageIO.read(file), WIDTH, HEIGHT);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        // 图片旋转
        image = ImgUtil.rotate(image, deg);
        // 需要进行裁剪，旋转后的图片会变大出现黑边，裁剪为原图形大小相同的内接圆大小的正方形即可
        image = ImgUtil.cut(image, new Rectangle((image.getWidth(null) - width) >> 1, (image.getHeight(null) - height) >> 1, width, height));
        // NEW: 切成圆形
        image = ImgUtil.cut(image, 0,0, WIDTH >> 1);
        return image;
    }

    public boolean verify(String key, String code) {
        Captcha captcha = cacheService.get(key, Captcha.class);
        int codeInt = Integer.parseInt(code);
        int captchaInt = Integer.parseInt(captcha.getCode());
        return Math.abs(captchaInt - codeInt) < TOLERANCE;
    }


}

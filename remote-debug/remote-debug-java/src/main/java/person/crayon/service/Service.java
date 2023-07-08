package person.crayon.service;

import java.util.Random;

/**
 * @author Crayon
 * @date 2022/10/17 9:44
 */
public class Service {
    public void printHello() {
        System.out.printf("[%s]: hello\n", this.getClass().getSimpleName());
    }

    public int genRandomNum() {
        Random random = new Random();
        return random.nextInt();
    }
}

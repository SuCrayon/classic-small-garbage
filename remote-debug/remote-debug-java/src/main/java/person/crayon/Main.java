package person.crayon;

import person.crayon.service.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Crayon
 * @date 2022/10/17 9:44
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main application running...");
        Service service = new Service();
        for (;;) {
            service.printHello();
            System.out.printf("randomNum is %d\n", service.genRandomNum());
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

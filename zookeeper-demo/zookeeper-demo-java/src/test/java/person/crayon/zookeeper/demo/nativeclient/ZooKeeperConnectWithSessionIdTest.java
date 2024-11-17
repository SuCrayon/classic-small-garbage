package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/10 10:46
 * 携带Session信息进行连接
 */
public class ZooKeeperConnectWithSessionIdTest {
    private static class DefaultWatch implements Watcher {
        private final String name;
        private final CountDownLatch latch;

        public DefaultWatch(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        public void process(WatchedEvent event) {
            System.out.printf("name: %s, event: %s\n", name, event);
            latch.countDown();
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        String connectString = "localhost:2181";
        int sessionTimeout = 4000;
        int count = 2;
        CountDownLatch latch0 = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(count);
        ZooKeeper zooKeeper0 = new ZooKeeper(connectString, sessionTimeout, new DefaultWatch("zookeeper0", latch0));
        latch0.await();
        long sessionId = zooKeeper0.getSessionId();
        byte[] sessionPasswd = zooKeeper0.getSessionPasswd();
        ZooKeeper zooKeeper1 = new ZooKeeper(connectString, sessionTimeout, new DefaultWatch("zookeeper1", latch), 1, "error-password".getBytes());
        ZooKeeper zooKeeper2 = new ZooKeeper(connectString, sessionTimeout, new DefaultWatch("zookeeper2", latch), sessionId, sessionPasswd);
        System.out.println("waiting...");
        latch.await();
        System.out.printf("zookeeper0 => sessionId: %d\n", sessionId);
        System.out.printf("zookeeper2 => sessionId: %d\n", zooKeeper2.getSessionId());
    }
}

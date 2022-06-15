package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/9 16:18
 * 客户端连接
 */
public class ZooKeeperConnectTest {
    private static final String CONNECT_STRING = "localhost:2181";

    public static ZooKeeper getConnect(Watcher watcher) throws IOException {
        return new ZooKeeper(CONNECT_STRING, 4000, watcher);
    }

    public static ZooKeeper getConnect(long sessionId, byte[] sessionPasswd, Watcher watcher) throws IOException {
        return new ZooKeeper(CONNECT_STRING, 4000, watcher, sessionId, sessionPasswd);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 4000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    // 如果收到了服务端的响应事件：连接成功
                    latch.countDown();
                    System.out.println("connected!");
                }
            }
        });
        System.out.println("waiting...");
        latch.await();
        System.out.println("State: " + zooKeeper.getState());
    }
}

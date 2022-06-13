package person.crayon;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/10 11:11
 * 同步节点创建
 */
public class SyncCreateNodeTest {
    public static ZooKeeper getConnect() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = ZooKeeperConnectTest.getConnect(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("connected!");
                    latch.countDown();
                }
            }
        });
        latch.await();
        return zooKeeper;
    }

    public static ZooKeeper getConnect(long sessionId, byte[] sessionPasswd) throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = ZooKeeperConnectTest.getConnect(sessionId, sessionPasswd, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("connected!");
                    latch.countDown();
                }
            }
        });
        latch.await();
        return zooKeeper;
    }

    @Test
    public void test() throws InterruptedException, IOException, KeeperException {
        ZooKeeper zooKeeper = getConnect();
        byte[] data = "crayon-data".getBytes("utf-8");
        String path = zooKeeper.create("/crayon", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("path: " + path);
        ZooKeeper zooKeeper1 = getConnect(zooKeeper.getSessionId(), zooKeeper.getSessionPasswd());
        Stat stat = zooKeeper1.exists("/crayon", false);
        byte[] nodeData = zooKeeper1.getData("/crayon", false, stat);
        System.out.println("data: " + new String(nodeData, "utf-8"));
    }
}

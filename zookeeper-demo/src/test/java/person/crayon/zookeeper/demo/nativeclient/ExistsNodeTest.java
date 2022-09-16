package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/9/6 15:29
 * 判断节点是否存在
 */
public class ExistsNodeTest {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        String path = "/crayon";
        zooKeeper = ZooKeeperConnectTest.getConnect(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        if (event.getType() == Event.EventType.None && event.getPath() == null) {
                            latch.countDown();
                        } else if (event.getType() == Event.EventType.NodeCreated) {
                            System.out.println("Node(" + event.getPath() + ")Created");
                            zooKeeper.exists(event.getPath(), true);
                        } else if (event.getType() == Event.EventType.NodeDeleted) {
                            System.out.println("Node(" + event.getPath() + ")Deleted");
                            zooKeeper.exists(event.getPath(), true);
                        } else if (event.getType() == Event.EventType.NodeDataChanged) {
                            System.out.println("Node(" + event.getPath() + ")DataChanged");
                            zooKeeper.exists(event.getPath(), true);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        latch.await();
        zooKeeper.exists(path, true);
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData(path, "123".getBytes(), -1);
        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.delete(path + "/c1", -1);
        zooKeeper.delete(path, -1);
        Thread.sleep(10 * 1000);
    }
}

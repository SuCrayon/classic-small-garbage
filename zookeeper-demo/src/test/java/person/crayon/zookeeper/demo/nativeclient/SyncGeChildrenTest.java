package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/14 16:33
 * 同步获取节点数据
 */
public class SyncGeChildrenTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException, BrokenBarrierException {
        final CountDownLatch latch = new CountDownLatch(2);
        final ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        System.out.println(zooKeeper.getChildren("/", false));
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        try {
                            System.out.println("re-get children: " + zooKeeper.getChildren(event.getPath(), true));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                }
            }
        };
        zooKeeper.getChildren("/", watcher);
        zooKeeper.delete("/crayon0", -1);
        zooKeeper.delete("/crayon1", -1);
        latch.await();
    }
}

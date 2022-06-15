package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/15 11:04
 * 同步获取节点数据
 */
public class SyncGetDataTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        final CountDownLatch latch = new CountDownLatch(1);
        zooKeeper.create("/crayon", "crayon".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(zooKeeper.getData("/crayon", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (event.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println("node data changed!");
                        latch.countDown();
                    }
                }
            }
        }, new Stat())));
        zooKeeper.setData("/crayon", "crayon".getBytes(), -1);
        latch.await();
    }
}

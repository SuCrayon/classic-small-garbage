package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/15 11:04
 * 异步获取节点数据
 */
public class AsyncGetDataTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        final CountDownLatch latch = new CountDownLatch(2);
        zooKeeper.create("/crayon", "crayon".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/crayon", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (event.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println("node data changed!");
                        latch.countDown();
                    }
                }
            }
        }, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("result code: " + rc);
                System.out.println("path: " + path);
                System.out.println("context: " + ctx);
                System.out.println("data: " + new String(data));
                System.out.println("stat: " + stat);
                latch.countDown();
            }
        }, null);
        zooKeeper.setData("/crayon", "crayon".getBytes(), -1);
        latch.await();
    }
}

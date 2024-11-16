package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/14 16:33
 * 异步获取节点数据
 */
public class AsyncGeChildrenTest {
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
        zooKeeper.getChildren("/", watcher, new AsyncCallback.ChildrenCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children) {
                System.out.println("result code: " + rc);
                System.out.println("path: " + path);
                System.out.println("ctx: " + ctx);
                System.out.println("children: " + children);
                latch.countDown();
            }
        }, null);
        zooKeeper.delete("/crayon0", -1);
        latch.await();
    }
}

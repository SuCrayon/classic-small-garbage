package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/10 11:11
 * 异步节点创建
 */
public class AsyncCreateNodeTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        final CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        byte[] data = "crayon-data".getBytes("utf-8");
        zooKeeper.create("/crayon", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println("rc: " + rc);
                System.out.println("path: " + path);
                System.out.println("ctx: " + ctx);
                System.out.println("name: " + name);
                latch.countDown();
            }
        }, "ctx");
        latch.await();
    }
}

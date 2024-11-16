package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/15 11:36
 * 异步更新数据
 */
public class AsyncSetDataTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        final CountDownLatch latch = new CountDownLatch(1);
        zooKeeper.create("/crayon", "crayon".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/crayon", false, null);
        zooKeeper.setData("/crayon", "crayon-modifyed".getBytes(), -1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("result code: " + rc);
                System.out.println("path: " + path);
                System.out.println("context: " + ctx);
                System.out.println(stat.getCzxid());
                System.out.println(stat.getMzxid());
                System.out.println(stat.getVersion());
                latch.countDown();
            }
        }, null);
        latch.await();
    }
}

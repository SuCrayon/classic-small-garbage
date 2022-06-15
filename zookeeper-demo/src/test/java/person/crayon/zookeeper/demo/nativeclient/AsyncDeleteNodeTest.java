package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crayon
 * @date 2022/6/14 16:36
 * 异步删除节点
 */
public class AsyncDeleteNodeTest {
    @Test
    public void test() throws IOException, InterruptedException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        final CountDownLatch latch = new CountDownLatch(3);
        AsyncCallback.VoidCallback cb = new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                switch (KeeperException.Code.get(rc)) {
                    case OK:
                        System.out.println("请求成功");
                        break;
                    case NONODE:
                        System.out.println("节点不存在");
                        break;
                    case BADVERSION:
                        System.out.println("节点版本不对应");
                        break;
                    default:
                }
                System.out.println("result code: " + rc);
                System.out.println("path: " + path);
                System.out.println("context: " + ctx);
                latch.countDown();
            }
        };
        // 删除不存在的节点，version为-1表示匹配任意version
        zooKeeper.delete("/inexist-node", -1, cb, "operation-0");
        // 删除存在的节点，但是version不对应
        zooKeeper.delete("/crayon", 1, cb, "operation-1");
        // 删除存在的节点，version对应
        zooKeeper.delete("/crayon", 0, cb, "operation-2");

        latch.await();
    }
}

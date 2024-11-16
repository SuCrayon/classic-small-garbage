package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Crayon
 * @date 2022/6/14 16:36
 * 同步删除节点
 */
public class SyncDeleteNodeTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        // 删除不存在的节点，version为-1表示匹配任意version
        zooKeeper.delete("/inexist-node", -1);
        // 删除存在的节点，但是version不对应
        zooKeeper.delete("/crayon", 1);
        // 删除存在的节点，version对应
        zooKeeper.delete("/crayon", 0);
    }
}

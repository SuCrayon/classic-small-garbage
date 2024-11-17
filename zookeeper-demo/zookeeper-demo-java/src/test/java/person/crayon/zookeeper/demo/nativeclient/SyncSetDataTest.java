package person.crayon.zookeeper.demo.nativeclient;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Crayon
 * @date 2022/6/15 11:36
 * 同步更新数据
 */
public class SyncSetDataTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = SyncCreateNodeTest.getConnect();
        zooKeeper.create("/crayon", "crayon".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/crayon", false, null);
        Stat stat = zooKeeper.setData("/crayon", "crayon-modifyed".getBytes(), -1);
        System.out.println(stat.getCzxid());
        System.out.println(stat.getMzxid());
        System.out.println(stat.getVersion());
    }
}

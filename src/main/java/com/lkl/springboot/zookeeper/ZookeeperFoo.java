package com.lkl.springboot.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 简单案例
 * 不错的文章：
 * http://www.ibm.com/developerworks/cn/opensource/os-cn-zookeeper/
 * Start zookeeper ：
 * 配置 conf/zoo.conf 
 *  tickTime=2000
    initLimit=10
    syncLimit=5
    dataDir=/Users/liaokailin/software/zookeeper-3.4.6/data
    dataLogDir=/Users/liaokailin/software/zookeeper-3.4.6/dataLog
    clientPort=2181
 * bin/zkServer.sh start 启动
 * bin/zkCli.sh 连接znode，ls / 查看跟目录下的所有znode
 * set / get 获取znode对应值
 * help 帮助文档
 * @author lkl
 * @version $Id: ZookeeperFoo.java, v 0.1 2015年8月20日 上午11:39:08 lkl Exp $
 */
public class ZookeeperFoo implements Watcher {

    /**
     * watch
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    private ZooKeeper zk;

    public ZookeeperFoo() throws IOException {
        this.zk = getZk();
    }

    public static void main(String[] args) {
        try {
            String znode = "/zk_node01";
            ZookeeperFoo zf = new ZookeeperFoo();
            // zf.createZnode(znode, "helloworld");
            //  zf.getZnodeDate(znode); //watch: state:SyncConnected type:None path:null
            zf.updateZnodeDate(znode, "12");
            // zf.isZnodeExists(znode); //WatchedEvent state:SyncConnected type:None path:null
            zf.getZnodeDate(znode);
        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ZooKeeper getZk() throws IOException {
        return new ZooKeeper("localhost:2181", 30000, this);
    }

    public void createZnode(String nodeName, String nodeDate) throws KeeperException, InterruptedException {
        //the actual path of the created node
        String path = this.zk.create(nodeName, nodeDate.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("path:" + path);
    }

    public void updateZnodeDate(String nodeName, String updateDate) throws KeeperException, InterruptedException {
        this.zk.setData(nodeName, updateDate.getBytes(), -1);
    }

    public void getZnodeDate(String nodeName) throws KeeperException, InterruptedException {
        byte[] data = this.zk.getData(nodeName, false, null);
        System.out.println(new String(data));
    }

    public void isZnodeExists(String nodeName) throws KeeperException, InterruptedException {
        Stat stat = this.zk.exists(nodeName, false);
        System.out.println(stat);
    }

}

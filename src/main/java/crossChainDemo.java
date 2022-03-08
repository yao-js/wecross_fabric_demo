import com.webank.wecrosssdk.rpc.WeCrossRPC;
import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;
import wecross.yjs.thread.CommitTxThread;
import wecross.yjs.thread.CrossChainTxThread;
import wecross.yjs.thread.LockResourceThread;
import wecross.yjs.thread.RollbackTxThread;

import static util.randomTxId.generateSHA256Hash;
import static util.randomTxId.getRandomString;

public class crossChainDemo {

    public static void main(String[] args) {

        // 定义随机的事务ID
        String fabric01TxId = generateSHA256Hash(getRandomString(16));
        String fabric02TxId = fabric01TxId;
//        String fabric02TxId = generateSHA256Hash(getRandomString(16));

        // 实例化跨链资源锁定线程实例
        LockResourceThread lockResourceThread1 = new LockResourceThread("lock_chain_resource", "org2-admin", "123456", "Fabric1.4",
                0, fabric01TxId, new String[]{"payment.fabric01.assetSample"});
        LockResourceThread lockResourceThread2 = new LockResourceThread("lock_chain_resource", "org2-admin", "123456", "Fabric1.4",
                5, fabric02TxId, new String[]{"payment.fabric02.assetSample"});

        // 实例化跨链事务操作线程实例
        CrossChainTxThread crossChainTxThread1 = new CrossChainTxThread("cross_chain_tx", "org2-admin", "123456", "Fabric1.4",
                0, fabric01TxId, "payment.fabric01.assetSample", "assetCut", new String[]{"a", "10"});
        CrossChainTxThread crossChainTxThread2 = new CrossChainTxThread("cross_chain_tx", "org2-admin", "123456", "Fabric1.4",
                5, fabric02TxId, "payment.fabric02.assetSample", "assetAdd", new String[]{"b", "10"});

        // 实例化跨链事务回滚线程实例
        RollbackTxThread rollbackTxThread1 = new RollbackTxThread("rollback_chain_tx", "org2-admin", "123456", "Fabric1.4",
                0, fabric01TxId, new String[]{"payment.fabric01.assetSample"});
        RollbackTxThread rollbackTxThread2 = new RollbackTxThread("rollback_chain_tx", "org2-admin", "123456", "Fabric1.4",
                5, fabric02TxId, new String[]{"payment.fabric02.assetSample"});
        // 实例化跨链事务提交线程实例
        CommitTxThread commitTxThread1 = new CommitTxThread("commit_chain_tx", "org2-admin", "123456", "Fabric1.4",
                0, fabric01TxId, new String[]{"payment.fabric01.assetSample"});
        CommitTxThread commitTxThread2 = new CommitTxThread("commit_chain_tx", "org2-admin", "123456", "Fabric1.4",
                5, fabric02TxId, new String[]{"payment.fabric02.assetSample"});



        try {
            // 开始执行跨链资源锁定线程
            lockResourceThread1.run();
            lockResourceThread1.join();
            if (!lockResourceThread1.getFinished()){
                // 当跨链资源锁定失败后，退出程序执行
                System.exit(0);
            }
            lockResourceThread2.run();
            lockResourceThread2.join();// 如果这一步失败了呢？需要添加一个if语句判断结果
            if (!lockResourceThread2.getFinished()){
                // 当第二条链锁定资源失败后，需要将第一条链锁定的资源释放掉
                rollbackTxThread1.run();
                rollbackTxThread1.join();
            }
            // 开始执行跨链事务操作线程
            crossChainTxThread1.run();
            crossChainTxThread1.join();
            if (!crossChainTxThread1.getFinished()){
                System.out.println("crossChainTxThread1要进行rollback啦！！");
                // 开始执行跨链回滚线程（针对fabric01链）
                rollbackTxThread1.run();
            } else {
                crossChainTxThread2.start();
                crossChainTxThread2.join();
                if (!crossChainTxThread2.getFinished() ){
                    System.out.println("两个调用资源的线程都要进行rollback啦！！");
                    // 开始执行跨链事务回滚线程（针对fabric01链以及fabric02链）
                    rollbackTxThread1.run();
                    rollbackTxThread1.join();
                    rollbackTxThread2.run();
                    rollbackTxThread2.join();
                } else {
                    // 开始执行跨链事务提交线程（提交fabric01链和fabric02链的跨链事务）
                    commitTxThread1.run();
                    commitTxThread1.join();
                    commitTxThread2.run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (!lockResourceThread1.isAlive() && !lockResourceThread2.isAlive() && !crossChainTxThread1.isAlive() && )
//            System.exit(0);
        }
    }
}

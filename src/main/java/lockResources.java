//import com.webank.wecrosssdk.exception.WeCrossSDKException;
//import com.webank.wecrosssdk.rpc.WeCrossRPC;
//import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
//import com.webank.wecrosssdk.rpc.methods.Callback;
//import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;
//import conf.Constant;
//import wecross.yjs.thread.LockResourceThread;
//
//import static util.randomTxId.generateSHA256Hash;
//import static util.randomTxId.getRandomString;
//
//public class lockResources {
//    public static void main(String[] args) {
//
//        // 定义随机的事务ID
//        String fabric01TxId = generateSHA256Hash(getRandomString(16));
//        String fabric02TxId = fabric01TxId;
//        // 实例化跨链资源锁定线程实例
//        LockResourceThread lockResourceThread1 = new LockResourceThread("lock_chain_resource", "org2-admin", "123456", "Fabric1.4",
//                0, fabric01TxId, new String[]{"payment.fabric01.assetSample"});
//        LockResourceThread lockResourceThread2 = new LockResourceThread("lock_chain_resource", "org2-admin", "123456", "Fabric1.4",
//                2, fabric02TxId, new String[]{"payment.fabric02.assetSample"});
//
////        try {
////            // 初始化 Service
////            WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
////
////            // 初始化Resource
////            WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
////
////            weCrossRPC.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
//////            Resource fabric01_resource = ResourceFactory.build(weCrossRPC, "payment.fabric01.mycc"); // RPC服务，资源的path
//////            Resource fabric02_resource = ResourceFactory.build(weCrossRPC, "payment.fabric02.mycc"); // RPC服务，资源的path
////
////            // 定义锁定资源的回调函数
////            Callback lockResources_callback = new Callback() {
////                @Override
////                public void onSuccess(Object o) {
////                    try {
////                        WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
////                        // 初始化Resource
////                        WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
////                        weCrossRPC.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
//////                        设置当前默认账号为fabric02中的Org1.msp
////                        weCrossRPC.setDefaultAccount("Fabric1.4", 2).send();
////                        // 开始锁定fabric02链中asset chaincode资源
////                        weCrossRPC.startXATransaction(constant.fabric02TxId, new String[]{"payment.fabric02.asset"}).send();
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    } finally {
////                        System.out.println("锁定资源成功！");
////                    }
////                }
////
////                @Override
////                public void onFailed(WeCrossSDKException e) {
////                    System.out.println("锁定资源失败！");
////                }
////            };
////
////            // 设置当前默认账号为fabric01中的Org1.msp
////            weCrossRPC.setDefaultAccount("Fabric1.4", 0).send();
////            // 开始锁定fabric01链中asset chaincode资源
////            weCrossRPC.startXATransaction(constant.fabric01TxId, new String[]{"payment.fabric01.asset"}).asyncSend(lockResources_callback);
////
////
////        } catch (Exception e) {
////            System.out.println("Error: " + e);
////        }
//    }
//}

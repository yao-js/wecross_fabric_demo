//import com.webank.wecrosssdk.exception.WeCrossSDKException;
//import com.webank.wecrosssdk.rpc.WeCrossRPC;
//import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
//import com.webank.wecrosssdk.rpc.methods.Callback;
//import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;
//
//public class commitTX {
//
//    public static void main(String[] args) throws Exception {
//
//        // 提交事务
//        WeCrossRPCService weCrossRPCService_final = new WeCrossRPCService();
//        WeCrossRPC weCrossRPC_final = WeCrossRPCFactory.build(weCrossRPCService_final);
//
//
//        Callback commit_callback = new Callback() {
//            WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
//            WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
//            @Override
//            public void onSuccess(Object o) {
//                try {
//                    weCrossRPC.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
//                    //设置当前默认账号为fabric02中的Org1.msp
//                    weCrossRPC.setDefaultAccount("Fabric1.4", 2).send();
//                    weCrossRPC.commitXATransaction(fabric02TxId, new String[]{"payment.fabric02.asset"}).send();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    System.out.println("事务提交成功！");
//                }
//            }
//
//            @Override
//            public void onFailed(WeCrossSDKException e) {
//                System.out.println("事务提交失败！");
//            }
//        };
//
//        //设置当前默认账号为fabric02中的Org1.msp
//        weCrossRPC_final.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
//        weCrossRPC_final.setDefaultAccount("Fabric1.4", 0).send();
//        weCrossRPC_final.commitXATransaction(constant.fabric01TxId, new String[]{"payment.fabric01.asset"}).asyncSend(commit_callback);
//    }
//}
//

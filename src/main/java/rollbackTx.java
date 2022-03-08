//import com.webank.wecrosssdk.exception.WeCrossSDKException;
//import com.webank.wecrosssdk.rpc.WeCrossRPC;
//import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
//import com.webank.wecrosssdk.rpc.methods.Callback;
//import com.webank.wecrosssdk.rpc.methods.response.XAResponse;
//import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;
//import conf.Constant;
//
//public class rollbackTx {
//
//    public static void main(String[] args) throws Exception {
//
//        Constant constant = new Constant();
//        WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
//        WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
//
//        // 回滚事务
//        try {
//            weCrossRPC.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
////                        设置当前默认账号为fabric01中的Org1.msp
//            weCrossRPC.setDefaultAccount("Fabric1.4", 0).send();
//            // 先对fabric01链进行rollback
//            weCrossRPC.rollbackXATransaction(constant.fabric01TxId, new String[]{"payment.fabric01.asset"}).asyncSend(new Callback<XAResponse>() {
//                WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
//                WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
//
//                @Override
//                public void onSuccess(XAResponse xaResponse) {
//                    try {
//                        weCrossRPC.login("org2-admin", "123456").send(); // 需要有登录态才能进一步操作
//                        //设置当前默认账号为fabric02中的Org1.msp
//                        weCrossRPC.setDefaultAccount("Fabric1.4", 2).send();
//                        weCrossRPC.rollbackXATransaction(constant.fabric02TxId, new String[]{"payment.fabric02.asset"}).send();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    } finally {
//                        System.out.println("回滚成功！");
//                    }
//                }
//
//                @Override
//                public void onFailed(WeCrossSDKException e) {
//                    System.out.println("回滚失败！");
//                }
//            });
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//}

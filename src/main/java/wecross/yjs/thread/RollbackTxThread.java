package wecross.yjs.thread;

import com.webank.wecrosssdk.exception.WeCrossSDKException;
import com.webank.wecrosssdk.rpc.WeCrossRPC;
import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
import com.webank.wecrosssdk.rpc.methods.Response;
import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;

public class RollbackTxThread extends Thread{

    private final String name;
    private String accountName;
    private String password;
    private String chainType;
    private Integer defaultAccountID;
    private String txId;
    private String[] chainResources;
    private Boolean isFinished;
    private String errorMessage;

    public RollbackTxThread(String name, String accountName, String password, String chainType, Integer defaultAccountID, String txId, String[] chainResources) {
        this.name = name;
        this.accountName = accountName;
        this.password = password;
        this.chainType = chainType;
        this.defaultAccountID = defaultAccountID;
        this.txId = txId;
        this.chainResources = chainResources;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void run() {
        try{
            WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
            WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
            weCrossRPC.login(this.accountName, this.password).send(); // 需要有登录态才能进一步操作
//                        设置当前默认账号为fabric01中的Org1.msp
            weCrossRPC.setDefaultAccount(this.chainType, this.defaultAccountID).send();

            Response response = weCrossRPC.rollbackXATransaction(this.txId, this.chainResources).send();
            if (response.getErrorCode() != 0) {
                setFinished(false);
                setErrorMessage(response.getMessage());
                System.out.println("跨链事务回滚失败！" + response.getMessage());
            } else {
                setFinished(true);
                System.out.println("跨链事务回滚成功！");
            }
        } catch (WeCrossSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

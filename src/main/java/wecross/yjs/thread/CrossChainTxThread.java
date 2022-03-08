package wecross.yjs.thread;

import com.webank.wecrosssdk.exception.WeCrossSDKException;
import com.webank.wecrosssdk.rpc.WeCrossRPC;
import com.webank.wecrosssdk.rpc.WeCrossRPCFactory;
import com.webank.wecrosssdk.rpc.methods.Callback;
import com.webank.wecrosssdk.rpc.methods.Response;
import com.webank.wecrosssdk.rpc.methods.response.TransactionResponse;
import com.webank.wecrosssdk.rpc.service.WeCrossRPCService;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class CrossChainTxThread extends Thread{

    private final String name;
    private String accountName;
    private String password;
    private String chainType;
    private Integer defaultAccountID;
    private String txId;
    private String chainResources;
    private String resourcesFunction;
    private String[] args;
    private Boolean isFinished;
    private String errorMessage;
    private String txData;

    public CrossChainTxThread(String name, String accountName, String password, String chainType, Integer defaultAccountID, String txId, String chainResources, String resourcesFunction, String[] args) {
        this.name = name;
        this.accountName = accountName;
        this.password = password;
        this.chainType = chainType;
        this.defaultAccountID = defaultAccountID;
        this.txId = txId;
        this.chainResources = chainResources;
        this.resourcesFunction = resourcesFunction;
        this.args = args;
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

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    @Override
    public void run() {
        try {
            // 初始化 Service
            WeCrossRPCService weCrossRPCService = new WeCrossRPCService();
            // 初始化Resource
            WeCrossRPC weCrossRPC = WeCrossRPCFactory.build(weCrossRPCService);
            weCrossRPC.login(this.accountName, this.password).send(); // 需要有登录态才能进一步操作
            //设置当前默认账号为fabric01中的Org1.msp
            weCrossRPC.setDefaultAccount(this.chainType, this.defaultAccountID).send();
            //开始通过fabric01发起跨链转账交易事务，首先先将fabric01链上的a账户扣除金额，然后将fabric02链上的b账户增加金额
            Response response = weCrossRPC.sendXATransaction(this.txId, this.chainResources, this.resourcesFunction, args).send();

            if (response.getErrorCode() != 0) {
                    setFinished(false);
                    setErrorMessage(response.getMessage());
                    System.out.println("调用事务资源失败！" + response.getMessage());
                } else {
                setFinished(true);
                // 记录跨链事务中对应的区块交易ID等信息
                setTxData(response.getData().toString());
                System.out.println("调用事务资源成功！交易信息: " + response.getData());
            }

//            weCrossRPC.sendXATransaction(this.txId, this.chainResources, this.resourcesFunction, args).asyncSend(new Callback<TransactionResponse>() {
//                @Override
//                public void onSuccess(TransactionResponse transactionResponse) {
//                    if (transactionResponse.getErrorCode() != 0) {
//                    setFinished(false);
//                    setErrorMessage(transactionResponse.getMessage());
//                    System.out.println("调用事务资源失败！" + transactionResponse.getMessage());
//                } else {
//                setFinished(true);
//                // 记录跨链事务中对应的区块交易ID等信息
//                setTxData(transactionResponse.getData().toString());
//                System.out.println("调用事务资源成功！交易信息: " + transactionResponse.getData());
//            }
//                }
//
//                @Override
//                public void onFailed(WeCrossSDKException e) {
//                    System.out.println("error:" + e.getMessage());
//                    System.out.println("error:" + e.getErrorCode());
//                }
//            });

        } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
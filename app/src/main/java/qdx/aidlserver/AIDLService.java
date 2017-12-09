package qdx.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


public class AIDLService extends Service {
    private static final int WHAT_MSG = 10010;
    private final String TAG = "qdx";

    private RemoteCallbackList<IDemandListener> demandList = new RemoteCallbackList<>();

    @Override
    public IBinder onBind(Intent intent) {
        mHandler.sendEmptyMessageDelayed(WHAT_MSG, 3000);
        return demandManager;
    }

    IDemandManager.Stub demandManager = new IDemandManager.Stub() {
        @Override
        public MessageBean getDemand() throws RemoteException {
            MessageBean demand = new MessageBean("首先，看到我要敬礼", 1);
            return demand;
        }

        @Override
        public void setDemandIn(MessageBean msg) throws RemoteException {//客户端数据流向服务端
            Log.i(TAG, "程序员:" + msg.toString());
        }

        @Override
        public void setDemandOut(MessageBean msg) throws RemoteException {//服务端数据流向客户端
            Log.i(TAG, "程序员:" + msg.toString());

            msg.setContent("我不想听解释，下班前把所有工作都搞好！");
            msg.setLevel(5);
        }

        @Override
        public void setDemandInOut(MessageBean msg) throws RemoteException {//数据互通
            Log.i(TAG, "程序员:" + msg.toString());

            msg.setContent("把用户交互颜色都改成粉色");
            msg.setLevel(3);
        }

        @Override
        public void registerListener(IDemandListener listener) throws RemoteException {
            demandList.register(listener);
        }

        @Override
        public void unregisterListener(IDemandListener listener) throws RemoteException {
            demandList.unregister(listener);
        }
    };

    private int count = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (demandList != null) {
                int nums = demandList.beginBroadcast();
                for (int i = 0; i < nums; i++) {
                    MessageBean messageBean = new MessageBean("我丢", count);
                    count++;
                    try {
                        demandList.getBroadcastItem(i).onDemandReceiver(messageBean);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                demandList.finishBroadcast();
            }

            mHandler.sendEmptyMessageDelayed(WHAT_MSG, 3000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(WHAT_MSG);
    }
}

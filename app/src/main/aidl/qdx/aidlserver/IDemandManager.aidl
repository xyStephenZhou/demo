package qdx.aidlserver;

import qdx.aidlserver.MessageBean;
import qdx.aidlserver.IDemandListener;

interface IDemandManager {

     MessageBean getDemand();

     void setDemandIn(in MessageBean msg);

     void setDemandOut(out MessageBean msg);

     void setDemandInOut(inout MessageBean msg);

     void registerListener(IDemandListener listener);

     void unregisterListener(IDemandListener listener);
}

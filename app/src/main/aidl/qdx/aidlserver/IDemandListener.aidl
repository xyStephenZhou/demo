package qdx.aidlserver;

import qdx.aidlserver.MessageBean;

interface IDemandListener {

     void onDemandReceiver(in MessageBean msg);

}

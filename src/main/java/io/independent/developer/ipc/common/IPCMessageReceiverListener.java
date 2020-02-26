package io.independent.developer.ipc.common;

import io.common.ipc.ResponseMsg;
import io.grpc.stub.StreamObserver;

public interface IPCMessageReceiverListener {

	void dispatchMsg(String key,String message,int msgType,StreamObserver<ResponseMsg> requestObserver);
}

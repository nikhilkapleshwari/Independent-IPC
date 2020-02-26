package io.independent.developer.ipc.service;

import io.common.ipc.ResponseMsg;
import io.grpc.stub.StreamObserver;
import io.independent.developer.ipc.common.IPCMessageSenderListener;

public abstract class ResponseHandler<T> implements StreamObserver<ResponseMsg> {

	IPCMessageSenderListener listener;
	
	public ResponseHandler(IPCMessageSenderListener listener) {
		this.listener = listener;
	}
	
	public void onNext(int code,String key,String message) {
		
		if(code==200)
			listener.onSuccess(key, message);
		else
			listener.onError(key,message,code);
	}


	public abstract void onNext(ResponseMsg responseMsg);

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub

	}

}

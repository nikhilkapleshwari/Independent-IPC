package io.independent.developer.ipc.common;

import io.grpc.stub.StreamObserver;

public interface IPCMessageSenderListener{
	
	public void onSuccess(String key,String msg);
	
	public void onError(String key,String msg,int code);
	
	public void onTimeout(String key,String msg);

}

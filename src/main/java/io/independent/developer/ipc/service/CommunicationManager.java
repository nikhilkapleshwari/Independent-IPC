package io.independent.developer.ipc.service;

import java.util.concurrent.TimeUnit;

import io.common.ipc.MessengerGrpc;
import io.common.ipc.MessengerGrpc.MessengerImplBase;
import io.common.ipc.MessengerGrpc.MessengerStub;
import io.common.ipc.RequestMsg;
import io.common.ipc.ResponseMsg;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.independent.developer.ipc.common.IPCMessageReceiverListener;
import io.independent.developer.ipc.common.IPCMessageSenderListener;
import io.independent.developer.ipc.endpoint.ServerEndpoint;

public class CommunicationManager extends MessengerImplBase implements Communicate {

	IPCMessageReceiverListener receiverListener;
	ServerEndpoint baseEndpoint;
	int port=Integer.getInteger("grpcPort",9029);
	String hostName=System.getProperty("grpcHostname","localhost");
	
	public CommunicationManager() {
	    this.baseEndpoint=new ServerEndpoint(port,this);
	}
	
	public CommunicationManager(IPCMessageReceiverListener receiverListener) {
	    this();
	    this.receiverListener = receiverListener;
	    this.baseEndpoint.init();
	    this.baseEndpoint.start();
	}

	
	@Override
	public void process(RequestMsg requestMsg,StreamObserver<ResponseMsg> requestObserver) {
		
		System.out.println("Request message:"+requestMsg.getMessage());
		
		this.receiverListener.dispatchMsg(requestMsg.getKey(),requestMsg.getMessage(),requestMsg.getMessageType(),requestObserver);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public void sendAsync(final String key, RequestMsg requestMsg, IPCMessageSenderListener senderListener) {

		final ManagedChannel channel = ManagedChannelBuilder.forAddress(hostName, port).usePlaintext(true).build();
		MessengerStub asyncStub = MessengerGrpc.newStub(channel);

		asyncStub = asyncStub.withDeadlineAfter(5000, TimeUnit.MILLISECONDS);

		asyncStub.process(requestMsg, new ResponseHandler<ResponseMsg>(senderListener) {

			@Override
			public void onNext(ResponseMsg value) {
				super.onNext(value.getCode(), value.getKey(), value.getMessage());
			}

		});

	}
	
	@Override
	public void stop() {
		this.baseEndpoint.stop();
	}
	
	@Override
	public void start() {
		this.baseEndpoint.start();
	}
}

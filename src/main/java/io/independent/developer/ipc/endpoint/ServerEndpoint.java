package io.independent.developer.ipc.endpoint;

import java.io.IOException;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerEndpoint{

	private int port;
	private Server server;
	private BindableService bindableService;
	
	public ServerEndpoint(int port,BindableService bindableService) {
		this.port = port;
		this.bindableService = bindableService;
	
	}

	public void init() {
		this.server=ServerBuilder.forPort(port).addService(bindableService).build();
	}
	
	public void start() {

		try {
				if(!this.server.isShutdown()) {
					this.server.start();
					System.out.println("Server started on port "+this.port+",for 30 secs");
					this.server.awaitTermination();				
				}else {
					System.out.println("Server already started.");
				}

			}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

	public void stop() {
		try {
			this.server.shutdown();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package io.independent.developer.ipc.service;

import io.grpc.BindableService;

public interface Communicate extends BindableService {

	public void start();
	public void stop();
	
}

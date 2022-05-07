package com.miyake.demo.jsonobject;

import lombok.Data;

@Data
public class WebSocketSignal {
	public enum SignalType {
		Signin,
		Signout,
		ResultUpdated,
		RequestTest, 
		RequestImage, 
		StopRequestImage,
		ImageReady, 
		MouseEvent, 
		
	}
	private SignalType signalType;
	private Object object;


	public WebSocketSignal() {}
	public WebSocketSignal(SignalType signalType, Object object) {
		this.signalType = signalType;
		this.object = object;
	}

}

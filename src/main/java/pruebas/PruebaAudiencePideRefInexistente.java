package pruebas;

import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.stub.AudienceCientHandlerStub;
import parball.arsApi.websocketclient.AudienceWebSocketClient;
import parball.arsApi.websocketclient.IAudienceClient;

public class PruebaAudiencePideRefInexistente {
	
	public static void main(String[] args) throws Exception {
		IAudienceClientHandler audienceHandler = new AudienceCientHandlerStub();
		IAudienceClient audienceClient = new AudienceWebSocketClient("Adri",audienceHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. s7He");
		audienceClient.checkReference("s7He");
		Thread.sleep(1000L);
		System.out.println("Me cierro");
		audienceClient.close();
	}
	
}

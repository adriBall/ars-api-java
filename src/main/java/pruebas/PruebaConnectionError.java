package pruebas;

import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.IMonitorClientHandler;
import parball.arsApi.stub.AudienceCientHandlerStub;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.stub.MonitorClientHandlerStub;
import parball.arsApi.websocketclient.AudienceWebSocketClient;
import parball.arsApi.websocketclient.IAudienceClient;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.IMonitorClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;
import parball.arsApi.websocketclient.MonitorWebSocketClient;

public class PruebaConnectionError {
	
	public static void main(String[] args) throws Exception {
		// El sevidor ha de estar apagado
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IAudienceClientHandler audienceHandler = new AudienceCientHandlerStub();
		IAudienceClient audienceClient = new AudienceWebSocketClient("Adri",audienceHandler);
		
		IMonitorClientHandler monitorHandler = new MonitorClientHandlerStub();
		IMonitorClient monitorClient = new MonitorWebSocketClient(monitorHandler);
		
		System.out.println("Me intento conectar");
		Thread.sleep(1000L);
		lecturerClient.close();
		audienceClient.close();
		monitorClient.close();
	}

}

package pruebas;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.IMonitorClientHandler;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.stub.MonitorClientHandlerStub;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.IMonitorClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;
import parball.arsApi.websocketclient.MonitorWebSocketClient;

public class PruebaMonitorPideRefExistente {
	
	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IMonitorClientHandler monitorHandler = new MonitorClientHandlerStub();
		IMonitorClient monitorClient = new MonitorWebSocketClient(monitorHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. 4tY5");
		monitorClient.checkReference("4tY5");
		Thread.sleep(200L);
		System.out.println("Cierro todo");
		lecturerClient.close();
		Thread.sleep(400L);
		monitorClient.close();
	}
	
}

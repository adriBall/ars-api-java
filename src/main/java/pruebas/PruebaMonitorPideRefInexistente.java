package pruebas;

import parball.arsApi.handler.IMonitorClientHandler;
import parball.arsApi.stub.MonitorClientHandlerStub;
import parball.arsApi.websocketclient.IMonitorClient;
import parball.arsApi.websocketclient.MonitorWebSocketClient;

public class PruebaMonitorPideRefInexistente {
	
	public static void main(String[] args) throws Exception {
		IMonitorClientHandler monitorHandler = new MonitorClientHandlerStub();
		IMonitorClient monitorClient = new MonitorWebSocketClient(monitorHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. s7He");
		monitorClient.checkReference("s7He");
		Thread.sleep(1000L);
		System.out.println("Me cierro");
		monitorClient.close();
	}
	
}

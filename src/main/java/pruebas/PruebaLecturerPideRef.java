package pruebas;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;

public class PruebaLecturerPideRef {
	
	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		Thread.sleep(500L);
		System.out.println("Recibo mi ref");
		Thread.sleep(4000L);
		System.out.println("La vuelvo a pedir");
		lecturerClient.getRef();
		Thread.sleep(500L);
		lecturerClient.close();
	}
	
}

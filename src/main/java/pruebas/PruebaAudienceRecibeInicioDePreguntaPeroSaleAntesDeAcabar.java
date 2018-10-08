package pruebas;

import parball.arsApi.common.Question;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.stub.AudienceCientHandlerStub;
import parball.arsApi.stub.EmptyQuestion;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.websocketclient.AudienceWebSocketClient;
import parball.arsApi.websocketclient.IAudienceClient;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;

public class PruebaAudienceRecibeInicioDePreguntaPeroSaleAntesDeAcabar {

	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IAudienceClientHandler audienceHandler = new AudienceCientHandlerStub();
		IAudienceClient audienceClient = new AudienceWebSocketClient("Adri",audienceHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. 4tY5");
		audienceClient.checkReference("4tY5");
		Thread.sleep(200L);
		System.out.println("El profesor envía una pregunta con timeout de 2 seg.");
		Question question = new EmptyQuestion();
		lecturerClient.sendQuestion(question, 2);
		Thread.sleep(200L);
		System.out.println("Salgo de clase antes de que termine la pregunta, no recibiré la respuesta");
		audienceClient.exitLecture();
		Thread.sleep(2000L);
		System.out.println("Cierro todo");
		audienceClient.close();
		lecturerClient.close();
	}
	
}

package pruebas;

import parball.arsApi.common.EAnswer;
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

public class PruebaAudienceRecibeRemainingTime {
	
	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IAudienceClientHandler audienceHandler = new AudienceCientHandlerStub();
		IAudienceClient audienceClient = new AudienceWebSocketClient("Adri",audienceHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. 4tY5");
		audienceClient.checkReference("4tY5");
		Thread.sleep(100L);
		System.out.println("Miro que no hay pregunta activa si no recibo nada");
		audienceClient.getRemainingTime();
		Thread.sleep(200L);
		System.out.println("El profesor envía una pregunta con timeout de 2 seg.");
		Question question = new EmptyQuestion();
		lecturerClient.sendQuestion(question, 2);
		Thread.sleep(200L);
		audienceClient.sendAnswer(EAnswer.C);
		Thread.sleep(200L);
		System.out.println("Compruebo el tiempo que queda");
		audienceClient.getRemainingTime();
		Thread.sleep(2000L);
		System.out.println("Cierro todo");
		audienceClient.close();
		Thread.sleep(400L);
		lecturerClient.close();
	}
	
}

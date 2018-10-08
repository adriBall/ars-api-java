package pruebas;

import parball.arsApi.common.Question;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.stub.EmptyQuestion;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;

public class PruebaLecturerEnviaPreguntaYOtra {

	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		Thread.sleep(500L);
		System.out.println("Envío pregunta con timeout de 5 seg.");
		Question question = new EmptyQuestion();
		lecturerClient.sendQuestion(question, 5);
		Thread.sleep(2000L);
		System.out.println("Pero a los 2 seg. envío otra, no tiene que tenerse en cuenta");
		lecturerClient.sendQuestion(question, 5);
		Thread.sleep(8000L);
		System.out.println("Me cierro");
		lecturerClient.close();
	}
	
}

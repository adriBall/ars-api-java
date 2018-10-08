package pruebas;

import parball.arsApi.common.Question;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.IMonitorClientHandler;
import parball.arsApi.stub.EmptyQuestion;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.stub.MonitorClientHandlerStub;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.IMonitorClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;
import parball.arsApi.websocketclient.MonitorWebSocketClient;

public class PruebaMonitorRecibeInicioYFinPregunta {
	
	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IMonitorClientHandler monitorHandler = new MonitorClientHandlerStub();
		IMonitorClient monitorClient = new MonitorWebSocketClient(monitorHandler);
		
		Thread.sleep(500L);
		System.out.println("Intento acceder a la clase con ref. 4tY5");
		monitorClient.checkReference("4tY5");
		Thread.sleep(500L);
		monitorClient.getCurrentQuestion();
		Thread.sleep(500L);
		Question question = new EmptyQuestion();
		question.setQuestion("Una pregunta?");
		question.setAnswerA("Una cosa");
		question.setAnswerB("Una cosa otra");
		question.setAnswerC("Ninguna");
		question.setAnswerD("Todas las anteriores");
		lecturerClient.sendQuestion(question, 5);
		Thread.sleep(2000L);
		System.out.println("La vuelvo a pedir");
		monitorClient.getCurrentQuestion();
		Thread.sleep(4000L);
		System.out.println("Cierro todo");
		monitorClient.close();
		Thread.sleep(400L);
		lecturerClient.close();
	}
	
}

package pruebas;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.Question;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.IMonitorClientHandler;
import parball.arsApi.stub.AudienceCientHandlerStub;
import parball.arsApi.stub.EmptyQuestion;
import parball.arsApi.stub.LecturerClientHandlerStub;
import parball.arsApi.stub.MonitorClientHandlerStub;
import parball.arsApi.websocketclient.AudienceWebSocketClient;
import parball.arsApi.websocketclient.IAudienceClient;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.IMonitorClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;
import parball.arsApi.websocketclient.MonitorWebSocketClient;

public class PruebaMonitorEnAccion {
	
	public static void main(String[] args) throws Exception {
		ILecturerClientHandler lecturerHandler = new LecturerClientHandlerStub();
		ILecturerClient lecturerClient = new LecturerWebSocketClient(lecturerHandler);
		
		IMonitorClientHandler monitorHandler = new MonitorClientHandlerStub();
		IMonitorClient monitorClient = new MonitorWebSocketClient(monitorHandler);
		
		IAudienceClientHandler audienceHandler = new AudienceCientHandlerStub();
		IAudienceClient audienceClient1 = new AudienceWebSocketClient("Adri1",audienceHandler);
		IAudienceClient audienceClient2 = new AudienceWebSocketClient("Adri2",audienceHandler);
		IAudienceClient audienceClient3 = new AudienceWebSocketClient("Adri3",audienceHandler);
		IAudienceClient audienceClient4 = new AudienceWebSocketClient("Adri4",audienceHandler);
		
		Thread.sleep(5000L);
		System.out.println("Intentan acceder a la clase con ref. 4tY5");
		monitorClient.checkReference("4tY5");
		audienceClient1.checkReference("4tY5");
		audienceClient2.checkReference("4tY5");
		audienceClient3.checkReference("4tY5");
		audienceClient4.checkReference("4tY5");
		Thread.sleep(500L);
		Question question = new EmptyQuestion();
		question.setQuestion("Una pregunta?");
		question.setAnswerA("Una cosa");
		question.setAnswerB("Una cosa otra");
		question.setAnswerC("Ninguna");
		question.setAnswerD("Todas las anteriores");
		lecturerClient.sendQuestion(question, 5);
		Thread.sleep(2000L);
		System.out.println("Alumnos contestan A, B, C y D");
		audienceClient1.sendAnswer(EAnswer.A);
		audienceClient2.sendAnswer(EAnswer.B);
		audienceClient3.sendAnswer(EAnswer.C);
		audienceClient4.sendAnswer(EAnswer.D);
		Thread.sleep(1000L);
		audienceClient2.getRemainingTime();
		audienceClient4.getRemainingTime();
		Thread.sleep(4000L);
		System.out.println("Cierro todo");
		monitorClient.close();
		audienceClient1.close();
		audienceClient2.close();
		audienceClient3.close();
		audienceClient4.close();
		Thread.sleep(400L);
		lecturerClient.close();
	}
	
}

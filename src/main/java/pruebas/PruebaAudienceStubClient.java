package pruebas;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.stub.AudienceStubClientSingletonFactory;
import parball.arsApi.websocketclient.IAudienceClient;

public class PruebaAudienceStubClient implements IAudienceClientHandler {
	IAudienceClient client;
	
	public static void main(String[] args) throws Exception {
		new PruebaAudienceStubClient().prueba();
	}
	
	private void prueba() throws Exception {
		client = AudienceStubClientSingletonFactory.getInstance("Adri", this, false);
		System.out.println("Me conecto");
		Thread.sleep(3000L);
		client.checkReference("P3N3");
		Thread.sleep(8000L);
		System.out.println("Contesto B");
		client.sendAnswer(EAnswer.B);
		Thread.sleep(500L);
		client.getRemainingTime();
		Thread.sleep(20000L);
		client.exitLecture();
		Thread.sleep(10000L);
		client.checkReference("asd");
		Thread.sleep(500L);
		client.checkReference("P3N3");
		Thread.sleep(10000L);
		client.exitLecture();
		Thread.sleep(500L);
		System.out.println("Cierro conexión");
		client.close();
	}

	@Override
	public void onRefChecked(ERefCheckedResolution resolution) {
		System.out.println("Ref checked resolution: "+resolution);
	}

	@Override
	public void onRemainingTimeReceived(long remainingTimeMillis, EAnswer answer) {
		System.out.println("Remaining time: "+remainingTimeMillis+" current answer "+answer);
	}

	@Override
	public void onQuestionStarted(long remainingTimeMillis) {
		System.out.println("Question started, "+remainingTimeMillis+" remaining.");
		client.getRemainingTime();
	}

	@Override
	public void onQuestionFinished(EAnswer correctAnswer) {
		System.out.println("Question finished, answer: "+correctAnswer);
	}

	@Override
	public void onLectureFinished() {
		System.out.println("Lecture finished");
	}

	@Override
	public void onConnectError() {
		System.out.println("Connection error");
	}

}

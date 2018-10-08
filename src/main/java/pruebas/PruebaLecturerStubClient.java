package pruebas;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.stub.EmptyQuestion;
import parball.arsApi.stub.LecturerStubClientSingletonFactory;
import parball.arsApi.websocketclient.ILecturerClient;

public class PruebaLecturerStubClient implements ILecturerClientHandler {
	
	public static void main(String[] args) throws Exception {
		new PruebaLecturerStubClient().prueba();
	}
	
	private void prueba() throws Exception {
		ILecturerClient client = LecturerStubClientSingletonFactory.getInstance(this, false);
		Thread.sleep(500L);
		System.out.println("Envío pregunta");
		client.sendQuestion(new EmptyQuestion(), 4);
		Thread.sleep(1000L);
		System.out.println("Cancelo pregunta");
		client.stopQuestion();
		Thread.sleep(3000L);
		client.getRef();
		Thread.sleep(500L);
		client.close();
		
	}

	@Override
	public void onRefReceived(String ref) {
		System.out.println("Received ref: "+ref);
	}

	@Override
	public void onQuestionFinished() {
		System.out.println("Question finished");
	}

	@Override
	public void onConnectError() {
		System.out.println("Connection error");
	}

}

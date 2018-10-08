package parball.arsApi.stub;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.websocketclient.ILecturerClient;

public class LecturerClientHandlerStub implements ILecturerClientHandler {
	ILecturerClient lecturerClient;

	@Override
	public void onRefReceived(String ref) {
		System.out.println("Lecturer: My ref. is "+ref);
	}

	@Override
	public void onQuestionFinished() {
		System.out.println("Lecturer: question finished");
	}
	
	@Override
	public void onConnectError() {
		System.out.println("Lecturer: connection error");
	}

}

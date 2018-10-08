package parball.arsApi.handler;

public interface ILecturerClientHandler {
	
	void onRefReceived(String ref);
	void onQuestionFinished();
	void onConnectError();

}

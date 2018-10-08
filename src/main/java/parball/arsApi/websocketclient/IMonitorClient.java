package parball.arsApi.websocketclient;

public interface IMonitorClient {
	
	void checkReference(String ref);
	void getCurrentQuestion();
	void exitLecture();
	void close();

}

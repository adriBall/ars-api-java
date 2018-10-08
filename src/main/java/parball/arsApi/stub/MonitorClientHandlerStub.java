package parball.arsApi.stub;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IMonitorClientHandler;

public class MonitorClientHandlerStub implements IMonitorClientHandler {

	@Override
	public void onRefChecked(ERefCheckedResolution resolution) {
		System.out.println("Monitor: ref. check resolved with "+resolution.toString());
	}

	@Override
	public void onLectureFinished() {
		System.out.println("Monitor: lecture finished");
	}

	@Override
	public void onQuestionReceived(String question, String answerA, String answerB, String answerC, String answerD,
			long remainingTimeMillis) {
		System.out.println("Monitor: question received, "+remainingTimeMillis+" remaining:"
				+ "\n\t-"+question
				+ "\n\t\t-"+answerA
				+ "\n\t\t-"+answerB
				+ "\n\t\t-"+answerC
				+ "\n\t\t-"+answerD);
	}

	@Override
	public void onQuestionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC,
			int numAnswersD) {
		System.out.println("Monitor: question finished, correct is "+correctAnswer+" :"
				+ "\n\t\tnum answers A: "+numAnswersA
				+ "\n\t\tnum answers B: "+numAnswersB
				+ "\n\t\tnum answers C: "+numAnswersC
				+ "\n\t\tnum answers D: "+numAnswersD);
	}

	@Override
	public void onConnectError() {
		System.out.println("Monitor: connection error");
	}

	@Override
	public void onGramificationReceived(String gamification, String splitRow, String splitColumn) {
		System.out.println("Monitor: gramification: \n"+gamification);
	}

}

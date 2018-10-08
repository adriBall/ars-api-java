package parball.arsApi.handler;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;

public interface IMonitorClientHandler {
	
	void onRefChecked(ERefCheckedResolution resolution);
	void onLectureFinished();
	void onQuestionReceived(String question, String answerA, String answerB, String answerC, String answerD, long remainingTimeMillis);
	void onQuestionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC, int numAnswersD);
	void onGramificationReceived(String gamification, String splitRow, String splitColumn);
	void onConnectError();
	
}

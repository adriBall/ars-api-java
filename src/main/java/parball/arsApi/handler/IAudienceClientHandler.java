package parball.arsApi.handler;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;

public interface IAudienceClientHandler {

	void onRefChecked(ERefCheckedResolution resolution);
	void onRemainingTimeReceived(long remainingTimeMillis, EAnswer currentAnswer);
	void onQuestionStarted(long remainingTimeMillis);
	void onQuestionFinished(EAnswer correctAnswer);
	void onLectureFinished();
	void onConnectError();
	
}

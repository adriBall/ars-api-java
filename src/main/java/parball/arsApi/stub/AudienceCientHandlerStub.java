package parball.arsApi.stub;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IAudienceClientHandler;

public class AudienceCientHandlerStub implements IAudienceClientHandler {

	@Override
	public void onRefChecked(ERefCheckedResolution resolution) {
		System.out.println("Audience: ref. check resolved with "+resolution.toString());
	}

	@Override
	public void onRemainingTimeReceived(long remainingTimeMillis, EAnswer currentAnswer) {
		System.out.println("Audience: the remaining time of the question is "+remainingTimeMillis+" msec."
				+" current answer "+currentAnswer);
	}

	@Override
	public void onQuestionStarted(long remainingTimeMillis) {
		System.out.println("Audience: question started, "+remainingTimeMillis+" remaining.");
	}

	@Override
	public void onQuestionFinished(EAnswer correctAnswer) {
		System.out.println("Audience: question finished, correct answer was "+correctAnswer.toString());
	}

	@Override
	public void onLectureFinished() {
		System.out.println("Audience: lecture finished");
	}
	
	@Override
	public void onConnectError() {
		System.out.println("Audience: connection error");
	}

}

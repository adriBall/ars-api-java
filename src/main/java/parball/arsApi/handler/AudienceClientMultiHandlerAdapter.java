package parball.arsApi.handler;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;

public class AudienceClientMultiHandlerAdapter implements IAudienceClientHandler {
	IAudienceClientHandler handler;
	
	public void setHandler(IAudienceClientHandler handler) {
		this.handler = handler;
	}

	@Override
	public void onRefChecked(ERefCheckedResolution resolution) {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onRefChecked(resolution);
	}

	@Override
	public void onRemainingTimeReceived(long remainingTimeMillis, EAnswer currentAnswer) {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onRemainingTimeReceived(remainingTimeMillis, currentAnswer);
	}

	@Override
	public void onQuestionStarted(long remainingTimeMillis) {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onQuestionStarted(remainingTimeMillis);
	}

	@Override
	public void onQuestionFinished(EAnswer correctAnswer) {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onQuestionFinished(correctAnswer);
	}

	@Override
	public void onLectureFinished() {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onLectureFinished();
	}
	
	@Override
	public void onConnectError() {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onConnectError();
	}
	
}

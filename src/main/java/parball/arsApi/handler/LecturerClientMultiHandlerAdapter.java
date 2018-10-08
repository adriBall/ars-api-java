package parball.arsApi.handler;

public class LecturerClientMultiHandlerAdapter implements ILecturerClientHandler {
	ILecturerClientHandler handler;
	
	public void setHandler(ILecturerClientHandler handler) {
		this.handler = handler;
	}

	@Override
	public void onRefReceived(String ref) {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onRefReceived(ref);
	}

	@Override
	public void onQuestionFinished() {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onQuestionFinished();
	}
	
	@Override
	public void onConnectError() {
		if(handler == null)
			throw new NoHandlerProvidedException();
		handler.onConnectError();
	}

}

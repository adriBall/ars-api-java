package parball.arsApi.stub;

import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.exceptions.WebsocketNotConnectedException;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.websocketclient.IAudienceClient;

public class AudienceStubClient implements IAudienceClient {
	private static final long LATENCY = 300L;
	private IAudienceClientHandler handler;
	private Timer timer;
	private volatile QuestionTimerTask questionTask;
	private volatile boolean connected;
	private boolean serverNotConnected;
	private volatile boolean inLecture;
	
	public AudienceStubClient(String username, IAudienceClientHandler handler, boolean serverNotConnected) {
		this.handler = handler;
		this.connected = false;
		this.inLecture = false;
		this.serverNotConnected = serverNotConnected;
		connect();
	}
	
	private void connect() {
		final AudienceStubClient client = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(LATENCY);
					if(connected == true)
						throw new IllegalStateException();
					if(serverNotConnected) {
						handler.onConnectError();
					}
					else {
						timer = new Timer();
						connected = true;
						Thread.sleep(5000L);
						
						TimerTask task = new TimerTask() {
							
							@Override
							public void run() {
								if(questionTask == null) {
									questionTask = new QuestionTimerTask(new EmptyQuestion(), client);
									timer.schedule(questionTask, 7000L);
									if(inLecture)
										handler.onQuestionStarted(7000L);
								}
							}
						};
						timer.scheduleAtFixedRate(task, 2000L, 8000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			
			}
		}).start();
	}

	public void questionFinished() {
		if(inLecture)
			handler.onQuestionFinished(questionTask.getQuestion().getCorrectAnswer());
		questionTask = null;
	}
	
	@Override
	public void checkReference(String ref) {
		final String refToCheck = ref;
		if(!connected)
			throw new WebsocketNotConnectedException();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(LATENCY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if("P3N3".equals(refToCheck) && !inLecture) {
					handler.onRefChecked(ERefCheckedResolution.SUCCESS);
					inLecture = true;
				}
				else if(!inLecture)
					handler.onRefChecked(ERefCheckedResolution.FAIL);
			}
		}).start();
		
	}

	@Override
	public void sendAnswer(EAnswer answer) {
		if(!connected)
			throw new WebsocketNotConnectedException();
		final EAnswer anAnswer = answer;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(LATENCY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(questionTask != null)
					questionTask.answer(anAnswer);
			}
		}).start();
	}

	@Override
	public void getRemainingTime() {
		if(!connected)
			throw new WebsocketNotConnectedException();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(LATENCY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(questionTask == null || !inLecture)
					handler.onRemainingTimeReceived(0L, EAnswer.NONE);
				else {
					handler.onRemainingTimeReceived(questionTask.getRemainingTime(), questionTask.getAnswer());
				}
			}
		}).start();
	}

	@Override
	public void exitLecture() {
		if(!connected)
			throw new WebsocketNotConnectedException();
		if(inLecture)
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(LATENCY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.onLectureFinished();
					inLecture = false;
				}
			}).start();
	}

	@Override
	public void close() {
		try {
			timer.cancel();
		} catch(Exception e) {}
		connected = false;
		inLecture = false;
	}

}

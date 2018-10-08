package parball.arsApi.stub;

import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.exceptions.WebsocketNotConnectedException;

import parball.arsApi.common.Question;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.websocketclient.ILecturerClient;

public class LecturerStubClient implements ILecturerClient {
	private static final long LATENCY = 300L;
	private ILecturerClientHandler handler;
	private Timer timer;
	private volatile TimerTask task;
	private volatile boolean connected;
	private boolean serverNotConnected;
	
	public LecturerStubClient(ILecturerClientHandler handler, boolean serverNotConnected) {
		this.handler = handler;
		this.connected = false;
		this.serverNotConnected = serverNotConnected;
		connect();
	}

	private void connect() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(LATENCY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(connected == true)
					throw new IllegalStateException();
				if(serverNotConnected) {
					handler.onConnectError();
				}
				else {
					timer = new Timer();
					connected = true;
					handler.onRefReceived("P3N3");
				}
			}
		}).start();	
	}

	@Override
	public void getRef() {
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
				handler.onRefReceived("P3N3");
			}
		}).start();
	}

	@Override
	public void sendQuestion(Question question, int timeOutSec) {
		if(!connected)
			throw new WebsocketNotConnectedException();
		if(task == null) {
			task = new TimerTask() {
				
				@Override
				public void run() {
					handler.onQuestionFinished();
					task = null;
				}
			};
			timer.schedule(task, timeOutSec*1000L);
		}
	}

	@Override
	public void stopQuestion() {
		if(!connected)
			throw new WebsocketNotConnectedException();
		if(task != null) {
			task.cancel();
			task = null;
			new Thread(new Runnable() {
						
				@Override
				public void run() {
					try {
						Thread.sleep(LATENCY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.onQuestionFinished();
				}
			}).start();
		}
	}

	@Override
	public void close() {
		timer.cancel();
		connected = false;
	}

}

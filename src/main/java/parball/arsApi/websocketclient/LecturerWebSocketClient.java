package parball.arsApi.websocketclient;

import java.io.StringReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import parball.arsApi.common.ELecturerClientActions;
import parball.arsApi.common.ELecturerServerActions;
import parball.arsApi.common.Question;
import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.keepalive.KeepAliveTask;

public class LecturerWebSocketClient extends WebSocketClient implements ILecturerClient {
	private static final String LECTURER_WEBSOCKET_SERVER_URI = "ws://localhost:8080/lecturer"; // TODO: Replace with your deploy url
	private static URI lecturerWebsocketServerURI;
	private ILecturerClientHandler lecturerHandler;
	private Timer timer;
	
	static {
		try {
			lecturerWebsocketServerURI = new URI(LECTURER_WEBSOCKET_SERVER_URI);
		} catch (URISyntaxException e) {
			Logger.getLogger(LecturerWebSocketClient.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public LecturerWebSocketClient(ILecturerClientHandler lecturerHandler) {
		super(lecturerWebsocketServerURI, new Draft_17());
		this.lecturerHandler = lecturerHandler;
		timer = new Timer();
		connect();
	}
	
	public LecturerWebSocketClient(ILecturerClientHandler lecturerHandler, URI uri) {
		super(uri, new Draft_17());
		this.lecturerHandler = lecturerHandler;
		connect();
	}
	
	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		if(arg0 == 1006 || arg0 == -1)
			lecturerHandler.onConnectError();
		timer.cancel();
	}

	@Override
	public void onError(Exception arg0) {
		System.out.println(arg0.getMessage());
		if(arg0 instanceof ConnectException) {
			lecturerHandler.onConnectError();
			System.out.println(arg0.getMessage());
		}
		else
			Logger.getLogger(LecturerWebSocketClient.class.getName()).log(Level.SEVERE, null, arg0);
		timer.cancel();
	}

	@Override
	public void onMessage(String arg0) {
		JsonReader reader = Json.createReader(new StringReader(arg0));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");
		
		if(ELecturerClientActions.SEND_REF.toString().equals(action)) {
			String ref = jsonMessage.getString("ref");
			lecturerHandler.onRefReceived(ref);
		}
		else if(ELecturerClientActions.QUESTION_FINISHED.toString().equals(action))
			lecturerHandler.onQuestionFinished();
		
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		timer.scheduleAtFixedRate(new KeepAliveTask(this),0L, 10000L);
	}
	
	@Override
	public synchronized void send(String text) {
		try {
			super.send(text);
		} catch (NotYetConnectedException  e) {
			lecturerHandler.onConnectError();
		} catch (WebsocketNotConnectedException e) {
			lecturerHandler.onConnectError();
		}
	}

	@Override
	public void getRef() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", ELecturerServerActions.GET_REF.toString())
        		.build();
        send(message.toString());
	}

	@Override
	public void sendQuestion(Question question, int timeOutSec) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", ELecturerServerActions.SEND_QUESTION.toString())
        		.add("question", question.getQuestion())
                .add("answerA", question.getAnswerA())
                .add("answerB", question.getAnswerB())
                .add("answerC", question.getAnswerC())
                .add("answerD", question.getAnswerD())
                .add("correctAnswer", question.getCorrectAnswer().toString())
                .add("timeOutSec", ""+timeOutSec)
                .build();
        send(message.toString());
	}

	@Override
	public void stopQuestion() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", ELecturerServerActions.STOP_QUESTION.toString())
                .build();
        send(message.toString());
	}

}

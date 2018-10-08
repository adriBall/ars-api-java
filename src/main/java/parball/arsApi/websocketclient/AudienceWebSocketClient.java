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

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.EAudienceClientActions;
import parball.arsApi.common.EAudienceServerActions;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.keepalive.KeepAliveTask;

public class AudienceWebSocketClient extends WebSocketClient implements IAudienceClient {
	private static final String AUDIENCE_WEBSOCKET_SERVER_URI = "ws://localhost:8080/audience"; // TODO: Replace with your deploy url
	private static URI audienceWebsocketServerURI;
	private IAudienceClientHandler audienceHandler;
	private String username;
	private Timer timer;
	
	static {
		try {
			audienceWebsocketServerURI = new URI(AUDIENCE_WEBSOCKET_SERVER_URI);
		} catch (URISyntaxException e) {
			Logger.getLogger(AudienceWebSocketClient.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public AudienceWebSocketClient(String username, IAudienceClientHandler audienceHandler) {
		super(audienceWebsocketServerURI, new Draft_17());
		this.audienceHandler = audienceHandler;
		this.username = username;
		timer = new Timer();
		connect();
	}

	public AudienceWebSocketClient(String username, IAudienceClientHandler audienceHandler, URI uri) {
		super(uri, new Draft_17());
		this.audienceHandler = audienceHandler;
		connect();
	}
	
	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		if(arg0 == 1006 || arg0 == -1)
			audienceHandler.onConnectError();
		timer.cancel();
	}

	@Override
	public void onError(Exception arg0) {
		if(arg0 instanceof ConnectException)
			audienceHandler.onConnectError();
		else
			Logger.getLogger(AudienceWebSocketClient.class.getName()).log(Level.SEVERE, null, arg0);
		timer.cancel();
	}

	@Override
	public void onMessage(String arg0) {
		JsonReader reader = Json.createReader(new StringReader(arg0));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");
		
		if(EAudienceClientActions.REF_CHECKED.toString().equals(action)) {
			ERefCheckedResolution resolution = ERefCheckedResolution.valueOf(jsonMessage.getString("resolution"));
			audienceHandler.onRefChecked(resolution);
		}
		else if(EAudienceClientActions.QUESTION_STARTED.toString().equals(action)) {
			long remainingTimeMillis = Long.parseLong(jsonMessage.getString("remainingTimeMillis"));
			audienceHandler.onQuestionStarted(remainingTimeMillis);
		}
		else if(EAudienceClientActions.LECTURE_FINISHED.toString().equals(action))
			audienceHandler.onLectureFinished();
		else if(EAudienceClientActions.SEND_REMAINING_TIME.toString().equals(action)) {
			long remainingTimeMillis = Long.parseLong(jsonMessage.getString("remainingTimeMillis"));
			EAnswer currentAnswer = EAnswer.valueOf(jsonMessage.getString("currentAnswer"));
			audienceHandler.onRemainingTimeReceived(remainingTimeMillis, currentAnswer);
		}
		else if(EAudienceClientActions.QUESTION_FINISHED.toString().equals(action)) {
			EAnswer correctAnswer = EAnswer.valueOf(jsonMessage.getString("correctAnswer"));
			audienceHandler.onQuestionFinished(correctAnswer);
		}
		
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        	.add("action", EAudienceServerActions.SET_USERNAME.toString())
        	.add("username", username)
        	.build();
        send(message.toString());
        timer.scheduleAtFixedRate(new KeepAliveTask(this), 0L, 10000L);
	}
	
	@Override
	public synchronized void send(String text) {
		try {
			super.send(text);
		} catch (NotYetConnectedException  e) {
			audienceHandler.onConnectError();
		} catch (WebsocketNotConnectedException e) {
			audienceHandler.onConnectError();
		}
	}

	@Override
	public void checkReference(String ref) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EAudienceServerActions.CHECK_REFERENCE.toString())
        		.add("ref", ref)
        		.build();
        send(message.toString());
	}
	
	@Override
	public void sendAnswer(EAnswer answer) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EAudienceServerActions.SEND_ANSWER.toString())
        		.add("answer", answer.toString())
        		.build();
        send(message.toString());
	}

	@Override
	public void getRemainingTime() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EAudienceServerActions.GET_REMAINING_TIME.toString())
        		.build();
        send(message.toString());
	}

	@Override
	public void exitLecture() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EAudienceServerActions.EXIT_LECTURE.toString())
        		.build();
        send(message.toString());
	}

}

package parball.arsApi.websocketclient;

import java.io.StringReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
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
import parball.arsApi.common.EMonitorClientActions;
import parball.arsApi.common.EMonitorServerActions;
import parball.arsApi.common.ERefCheckedResolution;
import parball.arsApi.handler.IMonitorClientHandler;

public class MonitorWebSocketClient extends WebSocketClient implements IMonitorClient {
	private static final String MONITOR_WEBSOCKET_SERVER_URI = "ws://localhost:8080/monitor"; // TODO: Replace with your deploy url
	private static URI monitorWebsocketServerURI;
	private IMonitorClientHandler monitorHandler;
	
	static {
		try {
			monitorWebsocketServerURI = new URI(MONITOR_WEBSOCKET_SERVER_URI);
		} catch (URISyntaxException e) {
			Logger.getLogger(MonitorWebSocketClient.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public MonitorWebSocketClient(IMonitorClientHandler monitorHandler) {
		super(monitorWebsocketServerURI, new Draft_17());
		this.monitorHandler = monitorHandler;
		connect();
	}
	
	public MonitorWebSocketClient(IMonitorClientHandler monitorHandler, URI uri) {
		super(uri, new Draft_17());
		this.monitorHandler = monitorHandler;
		connect();
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		if(arg0 == 1006 || arg0 == -1)
			monitorHandler.onConnectError();
	}

	@Override
	public void onError(Exception arg0) {
		if(arg0 instanceof ConnectException)
			monitorHandler.onConnectError();
		else
			Logger.getLogger(MonitorWebSocketClient.class.getName()).log(Level.SEVERE, null, arg0);
	}

	@Override
	public void onMessage(String arg0) {
		JsonReader reader = Json.createReader(new StringReader(arg0));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");
		
		if(EMonitorClientActions.REF_CHECKED.toString().equals(action)) {
			ERefCheckedResolution resolution = ERefCheckedResolution.valueOf(jsonMessage.getString("resolution"));
			monitorHandler.onRefChecked(resolution);
		}
		else if(EMonitorClientActions.LECTURE_FINISHED.toString().equals(action))
			monitorHandler.onLectureFinished();
		else if(EMonitorClientActions.SEND_QUESTION.toString().equals(action)) {
			String question = jsonMessage.getString("question");
			String answerA = jsonMessage.getString("answerA");
			String answerB = jsonMessage.getString("answerB");
			String answerC = jsonMessage.getString("answerC");
			String answerD = jsonMessage.getString("answerD");
			long remainingTimeMillis = Long.parseLong(jsonMessage.getString("remainingTimeMillis"));
			monitorHandler.onQuestionReceived(question, answerA, answerB, answerC, answerD, remainingTimeMillis);
		}
		else if(EMonitorClientActions.QUESTION_FINISHED.toString().equals(action)) {
			EAnswer correctAnswer = EAnswer.valueOf(jsonMessage.getString("correctAnswer"));
			int  numAnswersA = Integer.parseInt(jsonMessage.getString("numAnswersA"));
			int  numAnswersB = Integer.parseInt(jsonMessage.getString("numAnswersB"));
			int  numAnswersC = Integer.parseInt(jsonMessage.getString("numAnswersC"));
			int  numAnswersD = Integer.parseInt(jsonMessage.getString("numAnswersD"));
			monitorHandler.onQuestionFinished(correctAnswer, numAnswersA, numAnswersB, numAnswersC, numAnswersD);
		}
		else if(EMonitorClientActions.SEND_GAMIFICATION.toString().equals(action)) {
			String gamification = jsonMessage.getString("gamification");
			String splitRow = jsonMessage.getString("splitRow");
			String splitColumn = jsonMessage.getString("splitColumn");
			monitorHandler.onGramificationReceived(gamification, splitRow, splitColumn);
		}
		
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		// No hace nada
	}
	
	@Override
	public void send(String text) {
		try {
			super.send(text);
		} catch (NotYetConnectedException  e) {
			monitorHandler.onConnectError();
		} catch (WebsocketNotConnectedException e) {
			monitorHandler.onConnectError();
		}
	}

	@Override
	public void checkReference(String ref) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EMonitorServerActions.CHECK_REFERENCE.toString())
        		.add("ref", ref)
        		.build();
        send(message.toString());
	}

	@Override
	public void getCurrentQuestion() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EMonitorServerActions.GET_CURRENT_QUESTION.toString())
        		.build();
        send(message.toString());
	}

	@Override
	public void exitLecture() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
        		.add("action", EMonitorServerActions.EXIT_LECTURE.toString())
        		.build();
        send(message.toString());
	}

}

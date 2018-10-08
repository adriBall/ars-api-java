package parball.arsApi.keepalive;

import java.util.TimerTask;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import org.java_websocket.client.WebSocketClient;

public class KeepAliveTask extends TimerTask {
	private WebSocketClient client;
	
	public KeepAliveTask(WebSocketClient client) {
		this.client = client;
	}
	
	@Override
	public void run() {
			JsonProvider provider = JsonProvider.provider();
	        JsonObject message = provider.createObjectBuilder()
	        		.add("action", "RaR_RULES")
	        		.build();
			client.send(message.toString());
	}

}

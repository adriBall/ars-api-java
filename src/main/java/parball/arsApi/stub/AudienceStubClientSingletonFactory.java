package parball.arsApi.stub;

import parball.arsApi.handler.AudienceClientMultiHandlerAdapter;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.singleton.AudienceWebSocketClientSingletonFactory;
import parball.arsApi.websocketclient.IAudienceClient;

/**
 * Clase que proporciona siempre la misma instancia de un {@link IAudienceClient}, concretamente {@link AudienceStubClient}, 
 * obligando cada vez a proporcionar de nuevo un {@link IAudienceClientHandler}. Está orientado a ser utilizado en pruebas 
 * para ser reemplazado posteriormente por un {@link AudienceWebSocketClientSingletonFactory}.
 * @see IAudienceClient
 * @see AudienceStubClient
 * @see AudienceClientMultiHandlerAdapter
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public class AudienceStubClientSingletonFactory {
	private static IAudienceClient client;
	private static AudienceClientMultiHandlerAdapter handlerAdapter = new AudienceClientMultiHandlerAdapter();
	
	/**
	 * Método para obtener la única instancia del {@link AudienceStubClient}.
	 * @param username: username que tendrá el cliente en el servidor.
	 * @param handler: nuevo handler que recibirá las respuestas del servidor.
	 * @param serverNotConnected: true para simular que no se encuentra el servidor.
	 * @return cliente Lecturer, simulando que mantiene la conexión que tenía previamente.
	 */
	public static IAudienceClient getInstance(String username, IAudienceClientHandler handler, boolean serverNotConnected) {
		if(client == null)
			client = new AudienceStubClient(username, handlerAdapter, serverNotConnected);
		handlerAdapter.setHandler(handler);
		return client;
	}
	
	public static void destroyInstance() {
		client.close();
		client = null;
	}

}

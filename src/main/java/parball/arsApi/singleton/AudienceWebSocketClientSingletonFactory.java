package parball.arsApi.singleton;

import parball.arsApi.handler.AudienceClientMultiHandlerAdapter;
import parball.arsApi.handler.IAudienceClientHandler;
import parball.arsApi.websocketclient.AudienceWebSocketClient;
import parball.arsApi.websocketclient.IAudienceClient;

/**
 * Clase que proporciona siempre la misma instancia de un {@link IAudienceClient}, concretamente {@link AudienceWebSocketClient}, 
 * obligando cada vez a proporcionar de nuevo un {@link IAudienceClientHandler}. Está orientado a ser utilizado en Android, ya que
 * al cambiar de Actividad se puede necesitar mantener la misma conexión con el servidor, pero seguramente se querrá proporcionar
 * un nuevo handler que corresponda a la nueva Actividad.
 * @see IAudienceClient
 * @see AudienceWebSocketClient
 * @see AudienceClientMultiHandlerAdapter
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public class AudienceWebSocketClientSingletonFactory {
	private static IAudienceClient client;
	private static AudienceClientMultiHandlerAdapter handlerAdapter = new AudienceClientMultiHandlerAdapter();
	
	/**
	 * Método para obtener la única instancia del {@link AudienceWebSocketClient}.
	 * @param username: nombre de usuario.
	 * @param handler: nuevo handler que recibirá las respuestas del servidor.
	 * @return cliente Audience, manteniendo la conexión que tenía previamente.
	 */
	public static IAudienceClient getInstance(String username, IAudienceClientHandler handler) {
		if(client == null)
			client = new AudienceWebSocketClient(username, handlerAdapter);
		handlerAdapter.setHandler(handler);
		return client;
	}
	
	public static void destroyInstance() {
		client.close();
		client = null;
	}

}

package parball.arsApi.singleton;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.LecturerClientMultiHandlerAdapter;
import parball.arsApi.websocketclient.ILecturerClient;
import parball.arsApi.websocketclient.LecturerWebSocketClient;

/**
 * Clase que proporciona siempre la misma instancia de un {@link ILecturerClient}, concretamente {@link LecturerWebSocketClient}, 
 * obligando cada vez a proporcionar de nuevo un {@link ILecturerClientHandler}. Está orientado a ser utilizado en Android, ya que
 * al cambiar de Actividad se puede necesitar mantener la misma conexión con el servidor, pero seguramente se querrá proporcionar
 * un nuevo handler que corresponda a la nueva Actividad.
 * @see ILecturerClient
 * @see LecturerWebSocketClient
 * @see LecturerClientMultiHandlerAdapter
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public class LecturerWebSocketClientSingletonFactory {
	private static ILecturerClient client;
	private static LecturerClientMultiHandlerAdapter handlerAdapter = new LecturerClientMultiHandlerAdapter();
	
	/**
	 * Método para obtener la única instancia del {@link LecturerWebSocketClient}.
	 * @param handler: nuevo handler que recibirá las respuestas del servidor.
	 * @return cliente Lecturer, manteniendo la conexión que tenía previamente.
	 */
	public static ILecturerClient getInstance(ILecturerClientHandler handler) {
		if(client == null)
			client = new LecturerWebSocketClient(handlerAdapter);
		handlerAdapter.setHandler(handler);
		return client;
	}
	
	public static void destroyInstance() {
		client.close();
		client = null;
	}

}

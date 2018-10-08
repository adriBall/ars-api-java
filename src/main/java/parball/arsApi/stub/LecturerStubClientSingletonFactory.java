package parball.arsApi.stub;

import parball.arsApi.handler.ILecturerClientHandler;
import parball.arsApi.handler.LecturerClientMultiHandlerAdapter;
import parball.arsApi.singleton.LecturerWebSocketClientSingletonFactory;
import parball.arsApi.websocketclient.ILecturerClient;

/**
 * Clase que proporciona siempre la misma instancia de un {@link ILecturerClient}, concretamente {@link LecturerStubClient}, 
 * obligando cada vez a proporcionar de nuevo un {@link ILecturerClientHandler}. Está orientado a ser utilizado en pruebas 
 * para ser reemplazado posteriormente por un {@link LecturerWebSocketClientSingletonFactory}.
 * @see ILecturerClient
 * @see LecturerStubClient
 * @see LecturerClientMultiHandlerAdapter
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public class LecturerStubClientSingletonFactory {
	private static ILecturerClient client;
	private static LecturerClientMultiHandlerAdapter handlerAdapter = new LecturerClientMultiHandlerAdapter();
	
	/**
	 * Método para obtener la única instancia del {@link LecturerStubClient}.
	 * @param handler: nuevo handler que recibirá las respuestas del servidor.
	 * @param serverNotConnected: true para simular que no se encuentra el servidor.
	 * @return cliente Lecturer, simulando que mantiene la conexión que tenía previamente.
	 */
	public static ILecturerClient getInstance(ILecturerClientHandler handler, boolean serverNotConnected) {
		if(client == null)
			client = new LecturerStubClient(handlerAdapter, serverNotConnected);
		handlerAdapter.setHandler(handler);
		return client;
	}
	
	public static void destroyInstance() {
		client.close();
		client = null;
	}

}

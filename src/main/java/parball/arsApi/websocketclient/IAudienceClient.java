package parball.arsApi.websocketclient;

import parball.arsApi.common.EAnswer;
import parball.arsApi.handler.IAudienceClientHandler;

/**
 * Interfaz que debe implementar el cliente Audience para comunicarse con el servidor.
 * Esta interfaz solo abarca la comunicación hacia el servidor. {@link IAudienceClientHandler} se corresponde con la que viene del servidor. 
 * @see AudienceWebSocketClient
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public interface IAudienceClient {
	
	/**
	 * Solocita al servidor asociarse al Lecturer con referencia ref.
	 * @param ref: referencia del lecturer al que pretende asociarse.
	 */
	void checkReference(String ref);
	
	/**
	 * Envía al servidor la respuesta a una pregunta.
	 * Sólo tendrá efecto si se está asociado a algún Lecturer y hay alguna pregunta activa.
	 * @param answer: respuesta de la pregunta a enviar.
	 */
	void sendAnswer(EAnswer answer);
	
	/**
	 * Solicita al servidor que le envíe el tiempo que queda en segundos de la pregunta actual del Lecturer al que se está asociado.
	 */
	void getRemainingTime();
	
	/**
	 * Le indica al servidor que se desasocie del Lecturer al que se está asociado, si lo hay.
	 */
	void exitLecture();
	
	/**
	 * Método para cerrar la conexión con el servidor.
	 */
	void close();

}

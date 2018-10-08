package parball.arsApi.websocketclient;

import parball.arsApi.common.Question;
import parball.arsApi.handler.ILecturerClientHandler;

/**
 * Interfaz que debe implementar el cliente Lecturer para comunicarse con el servidor.
 * Esta interfaz solo abarca la comunicación hacia el servidor. {@link ILecturerClientHandler} se corresponde con la que viene del servidor. 
 * @see LecturerWebSocketClient
 * @author Adrián Enríquez Ballester & Pablo Berbel Marín
 *
 */
public interface ILecturerClient {
	
	/**
	 * Método para solicitar al servidor que envíe la referencia asignada a esta sesión.
	 */
	void getRef();
	
	/**
	 * 
	 * @param question: pregunta a enviar al servidor.
	 * @param timeOutSec: timeout de la pregunta en segundos. 
	 */
	void sendQuestion(Question question, int timeOutSec);
	
	/**
	 * Método para detener la pregunta actual.
	 * Sólo tiene efecto si hay alguna pregunta activa para esta sesión.
	 */
	void stopQuestion();
	
	/**
	 * Método para cerrar la conexión con el servidor.
	 */
	void close();
	
}

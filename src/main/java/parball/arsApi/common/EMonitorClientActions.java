package parball.arsApi.common;

public enum EMonitorClientActions {
	REF_CHECKED, // Params: action, resolution
	SEND_QUESTION, // Params: action, question, answerA, answerB, answerC, answerD, remainingTimeMillis
	SEND_GAMIFICATION, // params: action, gamification, splitRow, splitColumn
	QUESTION_FINISHED, // Params: action, correctAnswer, numAnswersA, numAnswersB, numAnswersC, numAnswersD
	LECTURE_FINISHED; // Params: action
}

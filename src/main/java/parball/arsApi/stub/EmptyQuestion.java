package parball.arsApi.stub;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.Question;

public class EmptyQuestion extends Question {
	
	public EmptyQuestion() {
		super();
		this.setQuestion("");
		this.setAnswerA("");
		this.setAnswerB("");
		this.setAnswerB("");
		this.setAnswerC("");
		this.setAnswerD("");
		this.setCorrectAnswer(EAnswer.A);
	}

}

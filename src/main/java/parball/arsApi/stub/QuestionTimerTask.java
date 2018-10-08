package parball.arsApi.stub;

import java.util.TimerTask;

import parball.arsApi.common.EAnswer;
import parball.arsApi.common.Question;

public class QuestionTimerTask extends TimerTask {
	private Question question;
	private AudienceStubClient client;
	private EAnswer answer;
	
	public QuestionTimerTask(Question question, AudienceStubClient client) {
		this.question = question;
		this.client = client;
		this.answer = EAnswer.NONE;
	}
	
	
	@Override
	public void run() {
		client.questionFinished();
	}
	
	public void answer(EAnswer answer) {
		if(!finished()) 
			if(this.answer.equals(EAnswer.NONE)) {
				this.answer = answer;
			}
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public boolean finished() {
		return getRemainingTime() == 0;
	}
	
	public long getRemainingTime() {
		return Math.max(scheduledExecutionTime() - System.currentTimeMillis(), 0);
	}
	
	public EAnswer getAnswer() {
		return answer;
	}

}

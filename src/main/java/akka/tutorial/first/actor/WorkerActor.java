package akka.tutorial.first.actor;

import akka.actor.UntypedActor;
import akka.tutorial.first.message.ResultMsg;
import akka.tutorial.first.message.WorkMsg;

public class WorkerActor extends UntypedActor {

	private double calculatePiFor(int start, int nrOfElements) {
		double acc = 0.0;
		for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
			acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
		}
		return acc;
	}

	/**
	 * Message handler
	 * @param message .
	 */
	@Override
	public void onReceive(Object message) {
		if (message instanceof WorkMsg) {
			WorkMsg workMsg = (WorkMsg) message;
			double result = calculatePiFor(workMsg.getStart(), workMsg.getNrOfElements());
			getSender().tell(new ResultMsg(result, Thread.currentThread().getName()), getSelf());
		} else {
			unhandled(message);
		}
	}
}
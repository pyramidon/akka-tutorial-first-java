package akka.tutorial.first.actor;

import akka.actor.UntypedActor;
import akka.tutorial.first.message.IntermediateResultMsg;
import akka.tutorial.first.message.PiApproximationMsg;

public class ListenerActor extends UntypedActor {
	/**
	 * Message handler
	 * @param message .
	 */
	@Override
	public void onReceive(Object message) {
		if (message instanceof PiApproximationMsg) {
			PiApproximationMsg approximation = (PiApproximationMsg) message;
			System.out.println(String.format(
					"\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s",
					approximation.getPi(), approximation.getDuration()));
			getContext().system().shutdown();
		} else if (message instanceof IntermediateResultMsg) {
			IntermediateResultMsg intermediateResultMsg = (IntermediateResultMsg) message;
			System.out.println(String.format(
					"%s\tIntermResult: \t%.15f\tDelta: \t%.15f\t%s",
					intermediateResultMsg.getCounter(), intermediateResultMsg.getIntermediateResult(),
					intermediateResultMsg.getDelta(), intermediateResultMsg.getThreadName().substring(20)));
		} else {
			unhandled(message);
		}
	}
}
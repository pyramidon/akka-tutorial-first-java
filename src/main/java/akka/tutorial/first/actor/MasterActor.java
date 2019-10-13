package akka.tutorial.first.actor;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;
import akka.tutorial.first.message.CalculateMsg;
import akka.tutorial.first.message.IntermediateResultMsg;
import akka.tutorial.first.message.PiApproximationMsg;
import akka.tutorial.first.message.ResultMsg;
import akka.tutorial.first.message.WorkMsg;
import akka.util.Duration;

public class MasterActor extends UntypedActor {
	// how many number chunks to send out to the workers
	private final int nrOfMessages;
	// how big the number chunks sent to each worker should be
	private final int nrOfElements;

	private double pi;
	// счетчик сообщений
	private int nrOfResults;

	private final long startTimeMillis = System.currentTimeMillis();

	private final ActorRef listener;
	// round-robin router to make it easier to spread out the work evenly between the workers
	// it's a router that is representing all our workers in a single abstraction
	private final ActorRef workerRouter;

	/**
	 *
	 * @param nrOfMessages how many number chunks to send out to the workers
	 *                     Сколько вызовов (сообщений) послать worker-ам
	 * @param nrOfElements how big the number chunks sent to each worker should be
	 *                     Сколько элементов ряда должен посчитать worker за один вызов (за одно сообщение)
	 * @param listener This is used to report the final result to the outside world
	 *                 Актор, которому посылается результат, чтобы представить его наружу
	 */
	public MasterActor(
			int nrOfMessages,
			int nrOfElements,
			ActorRef listener
	) {
		this.nrOfMessages = nrOfMessages;
		this.nrOfElements = nrOfElements;
		this.listener = listener;

		// установка параметров workerRouter из кода
		//workerRouter = this.getContext().actorOf(
		//		new Props(WorkerActor.class).withRouter(new RoundRobinRouter(nrOfWorkers)), "workerRouter");

		// установка параметров workerRouter из конфиг файла
		workerRouter = this.getContext().actorOf(
				new Props(WorkerActor.class).withRouter(new FromConfig()), "workerRouter");
	}

	/**
	 * Message handler
	 * @param message .
	 */
	@Override
	public void onReceive(Object message) {
		if (message instanceof CalculateMsg) {
			for (int start = 0; start < nrOfMessages; start++) {
				workerRouter.tell(new WorkMsg(start, nrOfElements), getSelf());
			}
		} else if (message instanceof ResultMsg) {
			ResultMsg resultMsg = (ResultMsg) message;
			pi += resultMsg.getValue();
			nrOfResults += 1;
			listener.tell(
					new IntermediateResultMsg(nrOfResults, resultMsg.getValue(), pi, resultMsg.getThreadName()),
					getSelf());
			if (nrOfResults == nrOfMessages) {
				// Send the result to the listener
				Duration duration = Duration.create(System.currentTimeMillis()
						- startTimeMillis, TimeUnit.MILLISECONDS);
				listener.tell(new PiApproximationMsg(pi, duration), getSelf());
				// Stops this actor and all its supervised children
				getContext().stop(getSelf());
			}
		} else {
			unhandled(message);
		}
	}
}
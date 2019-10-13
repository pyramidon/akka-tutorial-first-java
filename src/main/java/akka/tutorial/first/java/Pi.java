package akka.tutorial.first.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;
import akka.tutorial.first.actor.ListenerActor;
import akka.tutorial.first.actor.MasterActor;
import akka.tutorial.first.message.CalculateMsg;

/**
 * Akka example from: https://doc.akka.io/docs/akka/2.0.2/intro/getting-started-first-java.html
 */
public class Pi {

	public static void main(String[] args) {
		Pi pi = new Pi();
		pi.calculate(10000, 10000);
	}

	/**
	 *
//	 * @param nrOfWorkers how many workers we should start up
//	 *                    сколько worker-ов создать при старте системы
	 * @param nrOfElements how big the number chunks sent to each worker should be
	 *                     Сколько элементов ряда должен посчитать worker за один вызов (за одно сообщение)
	 * @param nrOfMessages how many number chunks to send out to the workers
	 *                     Сколько вызовов (сообщений) послать worker-ам
	 */
	public void calculate(final int nrOfElements, final int nrOfMessages) {
		// Create an Akka system
		ActorSystem system = ActorSystem.create("PiSystem");

		// create the result listener, which will print the result and shutdown
		// the system
		final ActorRef listener = system.actorOf(new Props(ListenerActor.class), "listener");

		// create the master
		ActorRef master = system.actorOf(
				new Props(
						(UntypedActorFactory) () -> new MasterActor(nrOfMessages, nrOfElements, listener)
				),
				"master");

		// start the calculation
		master.tell(new CalculateMsg());

	}
}
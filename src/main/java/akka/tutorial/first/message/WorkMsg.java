package akka.tutorial.first.message;

/**
 * Work – sent from the Master actor to the Worker actors containing the work assignment
 * сообщение от Master actor для Worker actors, содержащее назначенную для actor-а работу
 */
public class WorkMsg {
	private final int start;
	private final int nrOfElements;

	public WorkMsg(int start, int nrOfElements) {
		this.start = start;
		this.nrOfElements = nrOfElements;
	}

	public int getStart() {
		return start;
	}

	public int getNrOfElements() {
		return nrOfElements;
	}
}
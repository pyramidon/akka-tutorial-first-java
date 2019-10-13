package akka.tutorial.first.message;

/**
 * Result – sent from the Worker actors to the Master actor
 * containing the result from the worker’s calculation
 * сообщение от Worker actors для Master actor с результатом вычислений
 */
public class ResultMsg {
	private final double value;
	private final String threadName;

	public ResultMsg(double value, String threadName) {
		this.value = value;
		this.threadName = threadName;
	}

	public double getValue() {
		return value;
	}

	public String getThreadName() {
		return threadName;
	}
}
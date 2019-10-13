package akka.tutorial.first.message;

/**
 * IntermediateResult –
 * сообщение от Master actor с результатом промежуточных вычислений
 */
public class IntermediateResultMsg {
	private final double delta;
	private final long counter;
	// текущее значение результата
	private final double intermediateResult;
	private final String threadName;

	public IntermediateResultMsg(long counter, double delta, double intermediateResult, String threadName) {
		this.counter = counter;
		this.delta = delta;
		this.intermediateResult = intermediateResult;
		this.threadName = threadName;
	}

	public double getDelta() {
		return delta;
	}

	public long getCounter() {
		return counter;
	}

	public double getIntermediateResult() {
		return intermediateResult;
	}

	public String getThreadName() {
		return threadName;
	}
}
package akka.tutorial.first.message;

import akka.util.Duration;

/**
 * PiApproximation – sent from the Master actor to the Listener actor
 * containing the the final pi result and how long time the calculation took
 * сообщение от Master actor для Listener actor, содержащее число пи и время вычислений
 */
public class PiApproximationMsg {
	private final double pi;
	private final Duration duration;

	public PiApproximationMsg(double pi, Duration duration) {
		this.pi = pi;
		this.duration = duration;
	}

	public double getPi() {
		return pi;
	}

	public Duration getDuration() {
		return duration;
	}
}
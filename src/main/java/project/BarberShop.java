package project;

import static java.lang.Thread.startVirtualThread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BarberShop {

	public static final AtomicBoolean POS_IN_USE = new AtomicBoolean(false);
	public static final Barber barber1 = new Barber("Artur");
	public static final Barber barber2 = new Barber("Éber");
	public static final Barber barber3 = new Barber("Rafael");
	private static final int CAPACITY = 20;
	public static final AtomicReference<LinkedBlockingQueue<Client>> QUEUE = new AtomicReference<>(new LinkedBlockingQueue<>(CAPACITY));
	private static boolean hasStarted = false;

	public static void main(String[] args) {
		if (hasStarted) {
			return;
		}
		hasStarted = true;

		startVirtualThread(new LoggerStatus());
		startVirtualThread(new ClientFactory());
		final var threadBarber1 = startVirtualThread(barber1);
		final var threadBarber2 = startVirtualThread(barber2);
		final var threadBarber3 = startVirtualThread(barber3);

		try {
			threadBarber1.join();
			threadBarber2.join();
			threadBarber3.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
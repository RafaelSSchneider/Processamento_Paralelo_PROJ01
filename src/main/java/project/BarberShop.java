package project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BarberShop {

	public static final int CAPACITY_COUCH = 4;
	public static final int CAPACITY_QUEUE = 13;
	public static final AtomicBoolean POS_IN_USE = new AtomicBoolean(false);
	public static final List<Barber> BARBERS = List.of(new Barber("Artur"), new Barber("Éber"), new Barber("Rafael"));
	public static final ClientQueue COUCH = new ClientQueue(CAPACITY_COUCH);
	public static final ClientQueue QUEUE = new ClientQueue(CAPACITY_QUEUE);
	public static final AtomicReference<List<String>> CREATED_CLIENTS = new AtomicReference<>(new ArrayList<>());
	public static final AtomicReference<List<String>> CLIENTS_SERVED = new AtomicReference<>(new ArrayList<>());
	public static final AtomicReference<List<ISitsOnChair>> CHAIRS = new AtomicReference<>(new ArrayList<>());

	public static void main(String[] args) {
		BARBERS.forEach(barber -> CHAIRS.get().add(barber));

		new Thread(new LoggerStatus()).start();
		new Thread(new ClientFactory()).start();

		List<Thread> threadStream = BARBERS
				.stream()
				.map(Thread::new)
				.toList();

		try {
			threadStream.forEach(Thread::start);
		} catch (Exception e) {
			System.out.printf("Não foi possível iniciar as threads");
		}

		System.out.printf("Barbearia fechada");
	}
}

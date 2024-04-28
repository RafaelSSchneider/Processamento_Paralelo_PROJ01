package project;

import static project.BarberShop.CHAIRS;
import static project.BarberShop.CLIENTS_SERVED;
import static project.BarberShop.COUCH;
import static project.BarberShop.CREATED_CLIENTS;
import static project.BarberShop.QUEUE;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoggerStatus implements Runnable {

	public static final Duration LOG_INTERVAL = Duration.of(2, ChronoUnit.SECONDS);
	private static final Map<Class, Boolean> CLASS_ACTIVE_FOR_LOG = new HashMap<>() {{
		put(Barber.class, false);
		put(BarberShop.class, false);
		put(Client.class, false);
		put(ClientFactory.class, false);
		put(ClientQueue.class, false);
		put(Haircut.class, false);
		put(LoggerStatus.class, true);
		put(Utils.class, false);
	}};

	@SuppressWarnings("java:S2189")
	@Override
	public void run() {
		while (true) {
			System.out.printf("\n");
			logQueue();
			logCouch();
			logPOS();
			logBarbers();
			logChairs();
			System.out.printf("\n");
			logCreatedClients();
			logClientsServed();
			System.out.printf("\n");

			try {
				Thread.sleep(LOG_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void logQueue() {
		List<String> clientQueue = QUEUE
				.stream()
				.map(Client::getName)
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes na fila [%d]: %s", clientQueue.size(), clientQueue));
	}

	public static void logCouch() {
		List<String> clientCouch = COUCH
				.stream()
				.map(Client::getName)
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes no sofa [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logClientsServed() {
		List<String> clientCouch = CLIENTS_SERVED.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes atendidos [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logCreatedClients() {
		List<String> clientCouch = CREATED_CLIENTS.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes criados [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logChairs() {
		List<ISitsOnChair> chairs = CHAIRS.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Estão nas cadeiras [%d]: %s", chairs.size(), chairs));
	}

	public static void log(Class clazz, String string) {
		var on = Optional.of(CLASS_ACTIVE_FOR_LOG.get(clazz))
				.orElse(false);
		if (on) {
			String format = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
			System.out.printf("\n%s | %s", format, string);
		}
	}

	public static void logPOS() {
		log(LoggerStatus.class, "LOG | POS está em uso: " + (BarberShop.POS_IN_USE.get() ? "SIM" : "NÃO"));
	}

	private static void logBarbers() {
		BarberShop.BARBERS
				.forEach(barber -> log(LoggerStatus.class,
						String.format("LOG | Barbeiro %s está %s", barber.getName(), barber.getClientInAttendance() == null
								? "dormindo."
								: "atendendo o cliente " + barber.getClientInAttendance().getName())));
	}
}

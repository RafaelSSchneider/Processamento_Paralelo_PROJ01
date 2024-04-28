package project;

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
	private static final Map<Class, Boolean> classActiveForLog = new HashMap<>() {{
		put(Barber.class, true);
		put(BarberShop.class, false);
		put(Client.class, true);
		put(ClientFactory.class, false);
		put(ClientQueue.class, false);
		put(Haircut.class, true);
		put(LoggerStatus.class, true);
		put(Utils.class, true);
	}};

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
			logClientCriados();
			logClientAtendidos();
			System.out.printf("\n");

			try {
				Thread.sleep(LOG_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void logQueue() {
		List<String> clientQueue = BarberShop.QUEUE
				.stream()
				.map(Client::getName)
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes na fila [%d]: %s", clientQueue.size(), clientQueue));
	}

	public static void logCouch() {
		List<String> clientCouch = BarberShop.COUCH
				.stream()
				.map(Client::getName)
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes no sofa [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logClientAtendidos() {
		List<String> clientCouch = BarberShop.clientesAtendidos.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes atendidos [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logClientCriados() {
		List<String> clientCouch = BarberShop.clientesCriados.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Clientes criados [%d]: %s", clientCouch.size(), clientCouch));
	}

	public static void logChairs() {
		List<ISitsOnChair> chairs = BarberShop.chairs.get()
				.stream()
				.toList();
		log(LoggerStatus.class, String.format("LOG | Estão nas cadeiras [%d]: %s", chairs.size(), chairs));
	}

	public static void log(Class clazz, String string) {
		var on = Optional.of(classActiveForLog.get(clazz))
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

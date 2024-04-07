package project;

import static project.BarberShop.barber1;
import static project.BarberShop.barber2;
import static project.BarberShop.barber3;

import java.util.List;

public class LoggerStatus implements Runnable {

	@Override
	public void run() {
		while (true) {
			List<String> clientNames = BarberShop.QUEUE.get()
					.stream()
					.map(Client::getName)
					.toList();

			System.out.printf("\n\nClientes: " + clientNames);
			System.out.printf("\nBarbeiro: %s -> %s", barber1.getName(), barber1.getClientInAttendance());
			System.out.printf("\nBarbeiro: %s -> %s", barber2.getName(), barber2.getClientInAttendance());
			System.out.printf("\nBarbeiro: %s -> %s", barber3.getName(), barber3.getClientInAttendance());
			System.out.printf("\n");

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

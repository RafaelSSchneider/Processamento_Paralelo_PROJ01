package project;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class BarberShop {
	private static boolean hasStarted = false;

	public static AtomicReference<Queue> QUEUE = new AtomicReference<>(new PriorityQueue());

	public static void main(String[] args) {
		hasStarted = true;

		var barber1 = new Barber("Artur").startThread();
		var barber2 = new Barber("Éber").startThread();
		var barber3 = new Barber("Rafael").startThread();

		try {
			barber1.join();
			barber2.join();
			barber3.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		//create barbers
		//crete pos
		//create factory for consumers
		//create queue for consumers
		System.out.println("Hello world!");
	}
}
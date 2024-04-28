package project;

import static project.BarberShop.COUCH;
import static project.BarberShop.QUEUE;

public class ClientFactory implements Runnable {

	@SuppressWarnings("java:S2189")
	@Override
	public void run() {
		while (true) {
			Utils.randomSleep(1, 4);
			int remainingCapacity = COUCH.remainingCapacity();
			for (int i = 0; i < remainingCapacity; i++) {
				var clientFromQueue = QUEUE.poll();
				if (clientFromQueue != null) {
					COUCH.offer(clientFromQueue);
				}
			}
			var client = new Client();
			new Thread(client).start();
		}
	}
}

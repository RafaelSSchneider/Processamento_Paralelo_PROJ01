package project;

public class ClientFactory implements Runnable {

	@Override
	public void run() {
		while (true) {
			Utils.randomSleep(1, 4);
			int remainingCapacity = BarberShop.COUCH.remainingCapacity();
			for (int i = 0; i < remainingCapacity; i++) {
				var clientFromQueue = BarberShop.QUEUE.poll();
				if (clientFromQueue != null){
					BarberShop.COUCH.offer(clientFromQueue);
				}
			}
			var client = new Client();
			new Thread(client).start();
		}
	}
}

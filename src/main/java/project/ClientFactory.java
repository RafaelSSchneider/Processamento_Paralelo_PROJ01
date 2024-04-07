package project;

public class ClientFactory implements Runnable {

	@Override
	public void run() {
		Utils.randomSleep();
		BarberShop.QUEUE.get().offer(new Client());
	}
}

package project;

public class ClientFactory implements Runnable {

	@Override
	public void run() {
		while (true) {
			Utils.randomSleep(4);
			var newClient = new Client();
			var offer = BarberShop.QUEUE.get().offer(newClient);
			if (offer) {
				System.out.println("Cliente adicionado: " + newClient.getName());
			} else {
				System.out.println("Fila cheia, não foi possível adicionar o cliente " + newClient.getName());
			}
		}
	}
}

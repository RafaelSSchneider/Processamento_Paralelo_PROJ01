package project;

public class ClientFactory implements Runnable {

	@SuppressWarnings("java:S2189")
	@Override
	public void run() {
		while (true) {
			Utils.randomSleep(1, 2);
			var client = new Client();
			new Thread(client).start();
		}
	}
}

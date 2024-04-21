package project;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Objects.nonNull;
import static project.LoggerStatus.log;
import static project.Utils.sleep;

import java.util.concurrent.LinkedBlockingQueue;

public class ClientQueue extends LinkedBlockingQueue<Client> {

	public ClientQueue(int capacity) {
		super(capacity);
	}

	public void notifyClient(Barber barber) {
		log(this.getClass(), String.format("%s enviando notificação", barber.getName()));
		var client = this.poll();
		if (nonNull(client)) {
			barber.setClientInAttendance(client);
			client.setBarber(barber);
		}
		sleep(300, MILLIS);
		log(this.getClass(), String.format("%s finalizando notificação", barber.getName()));
	}
}

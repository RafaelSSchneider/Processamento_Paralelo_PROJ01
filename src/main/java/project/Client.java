package project;

import static project.BarberShop.BARBERS;
import static project.BarberShop.CLIENTS_SERVED;
import static project.BarberShop.COUCH;
import static project.BarberShop.CREATED_CLIENTS;
import static project.BarberShop.QUEUE;
import static project.LoggerStatus.log;
import static project.Utils.randomName;
import static project.Utils.randomNumber;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Client implements Runnable, ISitsOnChair {
	@Setter
	private boolean isSitting;
	private String name;
	private Haircut desiredHaircut;
	private Barber barber;

	public Client() {
		this.name = randomName();

		int randomIndex = randomNumber(0, Haircut.values().length);
		this.desiredHaircut = Haircut.values()[randomIndex];

		log(this.getClass(), String.format(this.name + " criado. Deseja: " + desiredHaircut.getName()));
		this.isSitting = false;
	}

	@Override
	public synchronized void run() {
		try {
			var offer = COUCH.offer(this);
			if (offer) {
				log(this.getClass(), String.format("Cliente adicionado sofá: %s ", this.getName()));
			} else {
				offer = QUEUE.offer(this);
				if (offer) {
					log(this.getClass(), String.format("Cliente adicionado a fila: %s ", this.getName()));
				} else {
					log(this.getClass(), String.format("Fila cheia, não foi possível adicionar o cliente " + this.getName()));
					return;
				}
			}
			CREATED_CLIENTS.get().add(this.name);

			BARBERS.parallelStream()
					.filter(barber1 -> barber1.getClientInAttendance() == null)
					.findAny()
					.ifPresent(Barber::notifyBarber);

			this.wait();
			log(this.getClass(), String.format("%s: está cortando o cabelo: %s ", this.name, barber.getName()));

			this.wait();
			log(this.getClass(), String.format("%s: está indo para casa", this.name));
			CLIENTS_SERVED.get().add(this.name);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void notifyClient() {
		this.notify();
	}

	public synchronized void setBarber(Barber barber) {
		log(this.getClass(), String.format("%s recebeu a notificacao do barbeiro %s", this.name, barber.getName()));
		this.barber = barber;
		this.notifyClient();
	}

	public String toString() {
		return this.getName();
	}
}

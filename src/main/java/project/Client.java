package project;

import static project.LoggerStatus.log;
import static project.Utils.randomName;
import static project.Utils.randomNumber;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Client implements Runnable {
	private String name;
	private Haircut desiredHaircut;
	private Barber barber;

	public Client() {
		this.name = randomName();

		int randomIndex = randomNumber(0, Haircut.values().length);
		this.desiredHaircut = Haircut.values()[randomIndex];

		log(this.getClass(), String.format(this.name + " created, they want " + desiredHaircut.getName()));
	}

	@Override
	public synchronized void run() {
		try {
			var offer = BarberShop.COUCH.offer(this);
			if (offer) {
				log(this.getClass(), String.format("Cliente adicionado sofá: %s ", this.getName()));
			} else {
				offer = BarberShop.QUEUE.offer(this);
				if (offer) {
					log(this.getClass(), String.format("Cliente adicionado a fila: %s ", this.getName()));
				} else {
					log(this.getClass(), String.format("Fila cheia, não foi possível adicionar o cliente " + this.getName()));
					return;
				}
			}
			BarberShop.clientesCriados.get().add(this.name);

			BarberShop.BARBERS.parallelStream()
					.filter(barber1 -> barber1.getClientInAttendance() == null)
					.findAny()
					.ifPresent(Barber::notifyBarber);

			this.wait();
			log(this.getClass(), String.format("%s: está cortando o cabelo: %s ", this.name, barber.getName()));

			this.wait();
			log(this.getClass(), String.format("%s: está indo para casa", this.name));
			BarberShop.clientesAtendidos.get().add(this.name);
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
}

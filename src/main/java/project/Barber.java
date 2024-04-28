package project;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Objects.nonNull;
import static project.LoggerStatus.log;
import static project.Utils.randomSleep;
import static project.Utils.sleep;

import lombok.Getter;

@Getter
public class Barber implements Runnable, ISitsOnChair {
	private final String name;
	private Client clientInAttendance;
	private boolean isSitting;

	public Barber(String name) {
		this.name = name;
		this.isSitting = true;
	}

	@Override
	public synchronized void run() {
		while (true) {
			BarberShop.COUCH.notifyClient(this);
			while (nonNull(clientInAttendance)) {
				Haircut clientHaircut = this.clientInAttendance.getDesiredHaircut();
				this.sitClientDown();
				log(this.getClass(), String.format("%s: Atendimento iniciado para o cliente %s", this.name, clientInAttendance.getName()));

				this.doHaircut(clientHaircut);

				log(this.getClass(), String.format("%s: Atendimento finalizado para o cliente %s", this.name, clientInAttendance.getName()));
				receivePayment();
				BarberShop.COUCH.notifyClient(this);
			}
			try {
				log(this.getClass(), String.format("%s: Dormindo", this.name));
				this.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log(this.getClass(), String.format("%s: Acordando", this.name));
		}
	}

	public synchronized void notifyBarber() {
		this.notify();
	}

	public synchronized void setClientInAttendance(Client clientInAttendance) {
		this.clientInAttendance = clientInAttendance;
		log(this.getClass(), String.format("%s recebeu o cliente %s ", this.name, this.clientInAttendance.getName()));
		this.notifyBarber();
	}

	public String toString() {
		return this.getName();
	}

	private synchronized void sitClientDown() {
		this.isSitting = false;
		this.clientInAttendance.isSitting = true;
		BarberShop.chairs.get().remove(this);
		BarberShop.chairs.get().add(this.clientInAttendance);
	}

	private void doHaircut(Haircut desiredHaircut) {
		log(this.getClass(), String.format("%s: está realizando: %s ", this.name, desiredHaircut.getName()));
		sleep(desiredHaircut.getTimeToCut(), SECONDS);
	}

	private void receivePayment() {
		while (BarberShop.POS_IN_USE.compareAndSet(false, true)) {
			log(this.getClass(), String.format(this.name + ": Iniciado pagamento do cliente " + getClientInAttendance().getName()));
			log(this.getClass(), String.format(this.name + ": Finalizado pagamento do cliente " + getClientInAttendance().getName()));
			randomSleep(1, 2);
		}
		BarberShop.POS_IN_USE.set(false);
		this.clientInAttendance.isSitting = false;
		BarberShop.chairs.get().remove(this.clientInAttendance);
		this.clientInAttendance.notifyClient();
		this.clientInAttendance = null;
	}
}

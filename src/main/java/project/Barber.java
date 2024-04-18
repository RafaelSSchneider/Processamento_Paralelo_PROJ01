package project;

import static java.util.Objects.nonNull;
import static project.Utils.randomSleep;
import static project.Utils.sleep;

import lombok.Getter;

@Getter
public class Barber implements Runnable {

	private final String name;
	private Client clientInAttendance;

	public Barber(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		while (true) {
			this.clientInAttendance = BarberShop.QUEUE.get().poll();
			while (nonNull(clientInAttendance)) {
				Haircut clientHaircut = this.clientInAttendance.getDesiredHaircut();
				System.out.println("Atendimento iniciado para o cliente " + clientInAttendance.getName());

				this.doHaircut(clientHaircut);

				System.out.println("Atendimento finalizado para o cliente " + clientInAttendance.getName());
				receivePayment();
				randomSleep(1, 2);
				this.clientInAttendance = BarberShop.QUEUE.get().poll();
			}
			//todo
		}
	}

	private void doHaircut(Haircut desiredHaircut) {
		System.out.println("Doing " + desiredHaircut.getName());
		sleep(desiredHaircut.getTimeToCut());
	}

	private void receivePayment() {
		while (BarberShop.POS_IN_USE.compareAndSet(false, true)) {
			System.out.println("Iniciado pagamento do client " + getClientInAttendance().getName());
			randomSleep(1, 2);
			System.out.println("Finalizado pagamento do client " + getClientInAttendance().getName());
		}
		this.clientInAttendance = null;
	}
}

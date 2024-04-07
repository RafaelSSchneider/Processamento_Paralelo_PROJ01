package project;

import static project.Utils.randomSleep;

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
		randomSleep();
		System.out.println(getName() + " Runned");
	}

	public void receivePayment() {
		while (BarberShop.POS_IN_USE.compareAndSet(false, true)) {
			System.out.println("Iniciado pagamento do client " + getClientInAttendance().getName());
			randomSleep();
			System.out.println("Finalizado pagamento do client " + getClientInAttendance().getName());
		}
		this.clientInAttendance = null;
	}
}

package project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Barber implements Runnable {

	private String name;

	@Override
	public void run() {
		System.out.println("Runned");
	}

	public Thread startThread() {
		return Thread.startVirtualThread(this);
	}
}

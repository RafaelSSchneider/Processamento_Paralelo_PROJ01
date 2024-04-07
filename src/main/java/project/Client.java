package project;

import static project.Utils.randomName;
import static project.Utils.randomSleep;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Client implements Runnable {

	private String name;

	public Client() {
		this.name = randomName();
	}

	@Override
	public void run() {
		randomSleep();
		System.out.println(getName() + " Runned");
	}
}

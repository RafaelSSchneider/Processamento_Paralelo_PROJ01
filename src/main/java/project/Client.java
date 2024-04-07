package project;

import static project.Utils.randomName;
import static project.Utils.randomSleep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Client implements Runnable {

	private String name;

	public Client() {
		this.name = randomName();
		System.out.println(this.name + " created");
	}

	@Override
	public void run() {
		randomSleep();
		System.out.println(getName() + " Runned");
	}
}

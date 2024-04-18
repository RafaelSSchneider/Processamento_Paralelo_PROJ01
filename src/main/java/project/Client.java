package project;

import static project.Utils.randomName;
import static project.Utils.randomNumber;
import static project.Utils.randomSleep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Client implements Runnable {
	private String name;
	private Haircut desiredHaircut;

	public Client() {
		this.name = randomName();

		int randomIndex = randomNumber(0, Haircut.class.getEnumConstants().length);
		this.desiredHaircut = Haircut.values()[randomIndex];

		System.out.println(this.name + " created, they want " + desiredHaircut.getName());
	}

	@Override
	public void run() {
		randomSleep();
		System.out.println(getName() + " Runned");
	}
}

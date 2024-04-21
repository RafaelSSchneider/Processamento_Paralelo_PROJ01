package project;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.github.javafaker.Faker;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	private static final Random RANDOM = new Random();

	public static void sleep(int amount, TemporalUnit temporalUnit) {
		try {
			Thread.sleep(Duration.of(amount, temporalUnit));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static int randomNumber(int minNumber, int maxNumber) {
		return RANDOM.nextInt(minNumber, maxNumber);
	}

	public static void randomSleep(int maxSeconds) {
		sleep(randomNumber(0, maxSeconds), SECONDS);
	}

	public static void randomSleep(int minSeconds, int maxSeconds) {
		sleep(randomNumber(minSeconds, maxSeconds), SECONDS);
	}

	public static String randomName() {
		return new Faker().name().fullName();
	}
}

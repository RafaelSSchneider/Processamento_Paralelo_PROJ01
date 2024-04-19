package project;

import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.github.javafaker.Faker;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	private static final Random RANDOM = new Random();

	public static void sleep(int secondsToSleep) {
		try {
			Thread.sleep(secondsToSleep * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static int randomNumber(int minNumber, int maxNumber) {
		return RANDOM.nextInt(minNumber, maxNumber);
	}

	public static void randomSleep() {
		try {
			Thread.sleep(RANDOM.nextLong(11) * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void randomSleep(int maxSeconds) {
		try {
			Thread.sleep(RANDOM.nextLong(maxSeconds) * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void randomSleep(int minSeconds, int maxSeconds) {
		try {
			Thread.sleep(RANDOM.nextLong(minSeconds, maxSeconds) * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomName() {
		return new Faker().name().fullName();
	}
}

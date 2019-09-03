package hello;

import org.joda.time.LocalTime;

public class HelloWorld {
	public static void main(String[] args) {
		LocalTime curTime = new LocalTime();
		System.out.println("Current local time: " + curTime);
		Greeter greeter = new Greeter();
		System.out.println(greeter.sayHello());
	}
}
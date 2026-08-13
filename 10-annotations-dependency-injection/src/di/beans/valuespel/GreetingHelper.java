package di.beans.valuespel;

import org.springframework.stereotype.Component;

/**
 * A plain bean whose method gets invoked from a SpEL expression elsewhere (see
 * AppInfo.greeting) -- demonstrates SpEL "referencing other bean
 * properties/methods" via the #{beanName.method(...)} syntax.
 */
@Component("greetingHelper")
public class GreetingHelper {

	public String buildGreeting(String appName) {
		return "Welcome to " + appName + "!";
	}

	public static String randomGreeting() {
		String[] greetings = { "Hello", "Hi", "Greetings", "Salutations", "Howdy", "Hey there", "Good day", "What's up",
				"Yo", "Ahoy", "Bonjour", "Hola", "Ciao", "Namaste", "Salaam", "Shalom", "Konnichiwa", "Guten Tag",
				"Sawasdee", "Marhaba" };
		int index = (int) (Math.random() * greetings.length);
		return greetings[index];
	}

}

package di.beans.valuespel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {

	// Plain property placeholder injection, from app.properties via @PropertySource
	@Value("${app.name}")
	private String appName;

	@Value("${app.version}")
	private String version;

	@Value("${app.maxUsers}")
	private int maxUsers;

	// Property placeholder with a DEFAULT VALUE (app.missingKey does not exist in
	// app.properties)
	@Value("${app.missingKey:DefaultValue}")
	private String withDefault;

	// Pure SpEL: arithmetic
	@Value("#{2 * 21}")
	private int spelArithmetic;

	// Pure SpEL: ternary, referencing an already-resolved property placeholder
	// inside the expression
	@Value("#{ (${app.maxUsers} > 50) ? 'HighCapacity' : 'LowCapacity' }")
	private String spelTernary;

	// SpEL referencing ANOTHER BEAN's method, itself passed a
	// property-placeholder-resolved argument
	@Value("#{greetingHelper.buildGreeting('${app.name}')}")
	private String greeting;

	// SpEL referencing a STATIC method on another class (no bean needed)
	@Value("#{ T(di.beans.valuespel.GreetingHelper).randomGreeting() }")
	private String randomGreeting;

	// All beans in the context are available as predefined SpEL variables by
	// their common bean name -- including these two standard context beans.
	@Value("#{systemProperties['os.name']}")
	private String osName;

	@Value("#{environment['app.name']}")
	private String appNameViaEnvironment;

	// Expression templating works NATIVELY inside @Value -- no
	// TemplateParserContext
	// needed here (contrast with the standalone demo in Main06 Part A, Section 10,
	// where TemplateParserContext has to be supplied explicitly).
	@Value("Your random ID is #{T(java.lang.Math).random()}")
	private String randomIdMessage;

	public void printStatus() {
		System.out.println("    [AppInfo] appName=" + appName);
		System.out.println("    [AppInfo] version=" + version);
		System.out.println("    [AppInfo] maxUsers=" + maxUsers);
		System.out.println("    [AppInfo] withDefault=" + withDefault
				+ "  (expected: DefaultValue, key absent from app.properties)");
		System.out.println("    [AppInfo] spelArithmetic=" + spelArithmetic + "  (expected: 42)");
		System.out.println(
				"    [AppInfo] spelTernary=" + spelTernary + "  (expected: HighCapacity, since maxUsers=100 > 50)");
		System.out.println("    [AppInfo] greeting=" + greeting);
		System.out.println("    [AppInfo] randomGreeting=" + randomGreeting);
		System.out.println("    [AppInfo] osName (predefined 'systemProperties' bean)          =" + osName);
		System.out.println(
				"    [AppInfo] appNameViaEnvironment (predefined 'environment' bean) =" + appNameViaEnvironment);
		System.out.println("    [AppInfo] randomIdMessage (templating works natively in @Value) =" + randomIdMessage);
	}

}

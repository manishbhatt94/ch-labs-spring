package di.main;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import di.beans.valuespel.AppInfo;
import di.beans.valuespel.City;
import di.beans.valuespel.Inventor;
import di.beans.valuespel.SimpleCalculator;
import di.beans.valuespel.ValueSpelConfig;

public class Main06_ValuePropertySourceSpEL {

	public static void main(String[] args) {

		System.out.println("=========================================================");
		System.out.println(" MAIN06: @Value, @PropertySource, and SpEL");
		System.out.println("=========================================================\n");

		// =====================================================================
		// PART A -- SpEL as a STANDALONE feature, with NO Spring container at
		// all. This is exactly how the "Spring Expression Language (SpEL)"
		// chapter of the docs introduces the API, before SpEL ever gets used
		// inside @Value or bean XML (that's Part B, further below).
		// =====================================================================

		System.out.println();
		System.out.println(
				"\n--- Part A, Section 1: ExpressionParser/Expression/EvaluationContext -- all getValue/setValue overloads ---\n");
		ExpressionParser parser = new SpelExpressionParser();

		Expression greetingExpr = parser.parseExpression("'Hello World'");
		System.out.println("    getValue()                                  -> " + greetingExpr.getValue());
		System.out.println("    getValue(Class)                             -> " + greetingExpr.getValue(String.class));

		Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
		Expression nameExpr = parser.parseExpression("name");

		System.out.println("    getValue(rootObject)                        -> " + nameExpr.getValue(tesla));
		System.out.println(
				"    getValue(rootObject, Class)                 -> " + nameExpr.getValue(tesla, String.class));

		EvaluationContext contextWithRoot = new StandardEvaluationContext(tesla);
		System.out.println("    getValue(context)  [root preset on context] -> " + nameExpr.getValue(contextWithRoot));
		System.out.println("    getValue(context, Class)                    -> "
				+ nameExpr.getValue(contextWithRoot, String.class));

		EvaluationContext bareContext = new StandardEvaluationContext(); // no root preset
		System.out
				.println("    getValue(context, rootObject)  [override]   -> " + nameExpr.getValue(bareContext, tesla));
		System.out.println("    getValue(context, rootObject, Class)        -> "
				+ nameExpr.getValue(bareContext, tesla, String.class));

		nameExpr.setValue(tesla, "Nikola Tesla #1");
		System.out.println("    setValue(rootObject, value)          -> tesla.name is now: " + tesla.getName());

		nameExpr.setValue(contextWithRoot, "Nikola Tesla #2");
		System.out.println("    setValue(context, value)             -> tesla.name is now: " + tesla.getName());

		nameExpr.setValue(bareContext, tesla, "Nikola Tesla #3");
		System.out.println("    setValue(context, rootObject, value) -> tesla.name is now: " + tesla.getName());

		System.out.println();
		System.out.println("\n--- Part A, Section 2: property navigation and indexing (arrays/strings/maps) ---\n");
		tesla.setPlaceOfBirth(new City("Smiljan"));
		tesla.setInventions(new String[] { "Tesla Coil", "AC Motor", "Radio" });

		System.out.println("    placeOfBirth.name                             -> "
				+ parser.parseExpression("placeOfBirth.name").getValue(tesla));
		System.out.println("    inventions[0]  (array indexing)               -> "
				+ parser.parseExpression("inventions[0]").getValue(tesla));
		System.out.println("    'Hello World'[0]  (string indexing)           -> "
				+ parser.parseExpression("'Hello World'[0]").getValue());
		System.out.println("    {'India':'Delhi','France':'Paris'}['France']  -> "
				+ parser.parseExpression("{'India':'Delhi','France':'Paris'}['France']").getValue());

		System.out.println();
		System.out.println("\n--- Part A, Section 3: inline lists, inline maps, array construction ---\n");
		System.out.println(
				"    {1,2,3,4}  (inline list)               -> " + parser.parseExpression("{1,2,3,4}").getValue());
		System.out.println("    {'India':'Delhi','France':'Paris'}     -> "
				+ parser.parseExpression("{'India':'Delhi','France':'Paris'}").getValue());
		System.out.println("    new int[]{1,2,3}  (array construction) -> "
				+ Arrays.toString((int[]) parser.parseExpression("new int[]{1,2,3}").getValue()));
		System.out.println("    (note: SpEL cannot take an initializer for a MULTI-dimensional array construction)");

		System.out.println();
		System.out.println("\n--- Part A, Section 4: invoking methods (on a literal, and on a root object) ---\n");
		System.out.println("    'Hello World'.concat('!')  (on a literal)   -> "
				+ parser.parseExpression("'Hello World'.concat('!')").getValue());
		System.out.println("    describe()  (instance method on rootObject) -> "
				+ parser.parseExpression("describe()").getValue(tesla));

		System.out.println();
		System.out.println("\n--- Part A, Section 5: the T() operator (types + static members) ---\n");
		System.out.println("    T(java.lang.Math).PI                       -> "
				+ parser.parseExpression("T(java.lang.Math).PI").getValue());
		System.out.println("    T(java.lang.Math).random()                 -> "
				+ parser.parseExpression("T(java.lang.Math).random()").getValue());
		System.out.println("    T(String) == T(java.lang.String)?  (java.lang needs no qualification) -> "
				+ parser.parseExpression("T(String) == T(java.lang.String)").getValue());
		System.out.println("    T(Math).toDegrees(3.141592653589793)       -> "
				+ parser.parseExpression("T(Math).toDegrees(3.141592653589793)").getValue());
		System.out.println("    T(di.beans.valuespel.GreetingHelper).randomGreeting()   "
				+ "(non-java.lang MUST be fully-qualified) -> "
				+ parser.parseExpression("T(di.beans.valuespel.GreetingHelper).randomGreeting()").getValue());
		System.out.println(
				"    T(di.beans.valuespel.City).getSimpleName()   " + "(calling Class<City>#getSimpleName() method) -> "
						+ parser.parseExpression("T(di.beans.valuespel.City).getSimpleName()").getValue());
		System.out.println("    T(di.beans.valuespel.Inventor)             -> "
				+ parser.parseExpression("T(di.beans.valuespel.Inventor)").getValue());

		System.out.println();
		System.out.println("\n--- Part A, Section 6: the Elvis operator (?:) ---\n");
		System.out.println("    nickname ?: 'Unknown'  (nickname was never set)    -> "
				+ parser.parseExpression("nickname ?: 'Unknown'").getValue(tesla));

		System.out.println();
		System.out.println("\n--- Part A, Section 7: the safe navigation operator (?.) ---\n");
		Inventor unknownInventor = new Inventor("Anonymous", "Unknown"); // placeOfBirth left null
		System.out.println("    placeOfBirth?.name  (placeOfBirth is null, no NPE) -> "
				+ parser.parseExpression("placeOfBirth?.name").getValue(unknownInventor));

		StandardEvaluationContext calcContext = new StandardEvaluationContext();
		calcContext.setVariable("calculator", null);
		System.out.println("    #calculator?.max(4, 2)  (calculator variable is null, no NPE) -> "
				+ parser.parseExpression("#calculator?.max(4, 2)").getValue(calcContext));

		System.out.println("    calc?.max(8, 11)  (`calc` field is null on rootObject `tesla`, STILL no NPE) -> "
				+ parser.parseExpression("calc?.max(8, 11)").getValue(tesla));
		// Now set the `calc` field on `tesla` to a real SimpleCalculator instance, and
		// try again:
		tesla.setCalc(new SimpleCalculator());
		System.out.println("    calc?.max(8, 11)  (`calc` field is now set on rootObject `tesla`) -> "
				+ parser.parseExpression("calc?.max(8, 11)").getValue(tesla));

		System.out.println();
		System.out.println("\n--- Part A, Section 8: collection selection (.?[], .^[], .$[]) ---\n");
		StandardEvaluationContext numbersContext = new StandardEvaluationContext();
		numbersContext.setVariable("numbers", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		System.out.println("    #numbers.?[#this % 2 == 0]  (all evens)  -> "
				+ parser.parseExpression("#numbers.?[#this % 2 == 0]").getValue(numbersContext));
		System.out.println("    #numbers.^[#this % 2 == 0]  (first even) -> "
				+ parser.parseExpression("#numbers.^[#this % 2 == 0]").getValue(numbersContext));
		System.out.println("    #numbers.$[#this % 2 == 0]  (last even)  -> "
				+ parser.parseExpression("#numbers.$[#this % 2 == 0]").getValue(numbersContext));
		System.out.println(
				"    {'India':'Delhi','France':'Paris','US':'DC'}.?[key.length() <= 2]  (map selection, by key) -> "
						+ parser.parseExpression("{'India':'Delhi','France':'Paris','US':'DC'}.?[key.length() <= 2]")
								.getValue());

		System.out.println();
		System.out.println("\n--- Part A, Section 9: collection projection (.![]) ---\n");
		Inventor pupin = new Inventor("Mihajlo Pupin", "Serbian");
		pupin.setPlaceOfBirth(new City("Idvor"));
		List<Inventor> inventors = Arrays.asList(tesla, pupin);

		StandardEvaluationContext inventorsContext = new StandardEvaluationContext();
		inventorsContext.setVariable("inventors", inventors);
		System.out.println("    #inventors.![placeOfBirth.name]  (list of birth cities)      -> "
				+ parser.parseExpression("#inventors.![placeOfBirth.name]").getValue(inventorsContext));
		System.out.println("    {'a':1,'b':2}.![value]  (map projection -> a List, per docs) -> "
				+ parser.parseExpression("{'a':1,'b':2}.![value]").getValue());

		System.out.println();
		System.out.println(
				"\n--- Part A, Section 10: expression templating (standalone -- needs TemplateParserContext) ---\n");
		String randomPhrase = parser
				.parseExpression("random number is #{T(java.lang.Math).random()}", new TemplateParserContext())
				.getValue(String.class);
		System.out.println("    " + randomPhrase);

		// =====================================================================
		// PART B -- SpEL as USED BY Spring, inside @Value on a managed bean.
		// This is the "Expressions in Bean Definitions" side of the docs --
		// everything here runs inside a real Spring ApplicationContext.
		// =====================================================================

		System.out.println();
		System.out.println("\n--- Part B: @Value, @PropertySource, and SpEL inside a Spring-managed bean ---\n");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ValueSpelConfig.class);
		ctx.getBean(AppInfo.class).printStatus();
		ctx.close();

		System.out.println();

	}

}

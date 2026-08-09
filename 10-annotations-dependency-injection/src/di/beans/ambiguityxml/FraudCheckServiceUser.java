package di.beans.ambiguityxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @formatter:off
 * Sub-case 3b: two FraudCheckServiceImpl beans exist, defined ENTIRELY in
 * XML, each with a nested <qualifier value="..."/> tag. This Java-side
 * @Autowired + @Qualifier("thorough") is what actually resolves the
 * ambiguity -- mixing an XML-declared bean+qualifier with an annotation-
 * driven injection point.
 * @formatter:on
 */
@Component
public class FraudCheckServiceUser {

	@Autowired
	@Qualifier("thorough")
	private FraudCheckService checker;

	public void printStatus() {
		System.out.println("    [FraudCheckServiceUser] checker=" + checker.describe()
				+ "  (expected: FraudCheckServiceImpl[Thorough], via XML <qualifier> + Java @Qualifier)");
	}

}

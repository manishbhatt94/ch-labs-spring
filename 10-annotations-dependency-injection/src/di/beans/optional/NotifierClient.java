package di.beans.optional;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * required=false on a SETTER: no SmsGateway bean exists, so per the docs "A
 * non-required method will not be called at all if its dependency ... is not
 * available." The field keeps its pre-set sentinel default, proving the setter
 * genuinely never ran.
 *
 * Deliberately NOT @Component -- it is registered explicitly and in isolation
 * by Main05 (Section 1), so it doesn't get swept up by the broader package scan
 * used later for Sections 3/4.
 */
public class NotifierClient {

	private SmsGateway smsGateway = null; // sentinel default: stays null if setter never runs

	@Autowired(required = false)
	public void setSmsGateway(SmsGateway smsGateway) {
		this.smsGateway = smsGateway;
		System.out.println("    [NotifierClient] setSmsGateway(...) WAS called (unexpected in this demo)");
	}

	public void printStatus() {
		System.out.println(
				"    [NotifierClient] smsGateway=" + (smsGateway == null ? "NULL (sentinel default kept)" : "SET")
						+ "  (expected: NULL -- setter should never have been invoked)");
	}

}

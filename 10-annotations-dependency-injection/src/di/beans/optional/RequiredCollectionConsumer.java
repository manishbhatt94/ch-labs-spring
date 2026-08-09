package di.beans.optional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Default required=true on a List<T> injection point. Per the docs: "In the
 * case of a declared array, collection, or map, at least one matching element
 * is expected." With ZERO UnregisteredType beans registered, this bean's
 * creation is expected to FAIL, not silently receive an empty list.
 *
 * Deliberately NOT @Component -- registered explicitly and in isolation by
 * Main05 (Section 2), so it doesn't get swept up by the broader package scan
 * used later for Sections 3/4.
 */
public class RequiredCollectionConsumer {

	@Autowired // default (required=true) attribute of @Autowired annotation is applied.
	private List<UnregisteredType> mustHaveAtLeastOne;

	public void printStatus() {
		System.out.println("    [RequiredCollectionConsumer] size=" + mustHaveAtLeastOne.size());
	}

}

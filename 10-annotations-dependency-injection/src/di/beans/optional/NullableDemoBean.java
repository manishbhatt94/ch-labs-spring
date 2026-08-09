package di.beans.optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @formatter:off
 * @Nullable (org.springframework.lang.Nullable here -- see study notes for
 * the JSpecify comparison) as a third way to express a non-required
 * dependency, alongside required=false and Optional<T>.
 * @formatter:on
 */
@Component
public class NullableDemoBean {

	private BackupContact backupContact;

	@Autowired
	public void assignBackupContact(@Nullable BackupContact backupContact) {
		this.backupContact = backupContact;
	}

	public void printStatus() {
		System.out.println("    [NullableDemoBean] backupContact=" + (backupContact == null ? "null" : "SET")
				+ "  (expected: null -- no bean registered, but no exception either)");
	}

}

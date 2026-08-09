package di.beans.ambiguity.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("email")
public class EmailNotificationService implements NotificationService {

	@Override
	public String describe() {
		return "Email";
	}

}

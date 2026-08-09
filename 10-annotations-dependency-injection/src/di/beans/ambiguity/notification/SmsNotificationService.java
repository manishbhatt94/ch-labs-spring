package di.beans.ambiguity.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("sms")
public class SmsNotificationService implements NotificationService {

	@Override
	public String describe() {
		return "SMS";
	}

}

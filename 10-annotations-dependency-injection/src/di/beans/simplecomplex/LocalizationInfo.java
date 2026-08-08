package di.beans.simplecomplex;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalizationInfo {

	@Autowired
	private String[] supportedLanguages;

	public void printStatus() {
		System.out.println("    [LocalizationInfo] supportedLanguages=" + Arrays.toString(supportedLanguages));
	}

}

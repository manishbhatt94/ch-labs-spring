package di.beans.collections.news;

import org.springframework.stereotype.Component;

@Component
public class CnnNewsSource implements NewsSource {

	@Override
	public String name() {
		return "CNN";
	}

}

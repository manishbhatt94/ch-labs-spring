package di.beans.collections.news;

import org.springframework.stereotype.Component;

@Component
public class BbcNewsSource implements NewsSource {

	@Override
	public String name() {
		return "BBC";
	}

}

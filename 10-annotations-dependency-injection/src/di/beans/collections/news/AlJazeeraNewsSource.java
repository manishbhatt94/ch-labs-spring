package di.beans.collections.news;

import org.springframework.stereotype.Component;

@Component
public class AlJazeeraNewsSource implements NewsSource {

	@Override
	public String name() {
		return "AlJazeera";
	}

}

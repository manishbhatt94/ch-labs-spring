package di.beans.collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.collections.news.NewsSource;

/**
 * None of the NewsSource beans declare @Order or implement Ordered, so per the
 * docs, "their order follows the registration order of the corresponding target
 * bean definitions in the container." This is NOT a formally
 * documented/guaranteed sequence for us to rely on in real code -- it's shown
 * here purely to contrast against the two deliberate-ordering mechanisms below
 * (@Order and Ordered).
 */
@Component
public class NewsAggregatorUnordered {

	@Autowired
	private List<NewsSource> sources;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (NewsSource s : sources) {
			sb.append(s.name()).append(" ");
		}
		System.out.println("    [NewsAggregatorUnordered] order=" + sb.toString().trim()
				+ "      (natural registration order -- not a guaranteed contract)");
	}

}

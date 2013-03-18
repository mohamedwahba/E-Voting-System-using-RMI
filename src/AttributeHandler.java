import java.util.HashMap;
import java.util.StringTokenizer;

public class AttributeHandler {
	HashMap<String, String> attr;

	public AttributeHandler(String query) {
		StringTokenizer temp = new StringTokenizer(query, "[&?=]+");
		attr = new HashMap<String, String>();
		String prev = temp.nextToken();
		while (temp.hasMoreTokens()) {
			String cur = temp.nextToken();
			attr.put(prev, cur);
			prev = cur;
		}
	}

	public String getAttr(String s) {
		return attr.get(s);
	}
}

package sea.nlp.comparator;

import java.util.Comparator;

import sea.nlp.pos.Context;

public class ContextCounter implements Comparator<Context>{

	@Override
	public int compare(Context context1, Context context2) {
		if (context1.getContextCounter() < context2.getContextCounter())
			return 1;
		else
			return -1;
	}

}

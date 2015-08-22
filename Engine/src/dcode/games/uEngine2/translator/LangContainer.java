package dcode.games.uEngine2.translator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dusakus on 09.03.15.
 */
public class LangContainer {
	public String langId;

	LangTreeBranch LTB;

	public LangContainer(String language) {
		langId = language;
		LTB = new LangTreeBranch("BASE");
	}

	public String getTransFor(String IN) {
		return LTB.getValue(IN);
	}

	public void addTransFor(String key, String translateTo) {
		LTB.addValue(key, translateTo);
	}

	public class LangTreeBranch {
		public String Key;
		Map<String, LangTreeLeaf> leaves = new HashMap<String, LangTreeLeaf>();
		Map<String, LangTreeBranch> branches = new HashMap<String, LangTreeBranch>();
		private boolean isROOT = false;

		public LangTreeBranch(String key) {
			Key = key;
			if (key.equals("BASE")) {
				isROOT = true;
			}
		}

		public void addValue(String key, String value) {
			if (key.contains("|")) {
				System.out.println("Branch " + key.substring(key.indexOf("|") + 1));
				if (branches.get(key.substring(key.indexOf("|") + 1)) != null) {
					branches.get(key.substring(key.indexOf("|") + 1)).addValue(key.substring(key.indexOf("|") + 1, key.length()), value);
				} else {
					branches.put(key.substring(key.indexOf("|") + 1), new LangTreeBranch(key.substring(key.indexOf("|") + 1)));
					addValue(key, value);
				}
			} else {
				leaves.put(key, new LangTreeLeaf(value, key));
			}
		}

		public String getValue(String key) {
			if (key.contains("|")) {
				if (branches.get(key.substring(key.indexOf("|") + 1)) != null) {
					return branches.get(key.substring(key.indexOf("|") + 1)).getValue(key.substring(key.indexOf("|") + 1, key.length()));
				} else {
					return key;
				}
			} else {
				return leaves.get(key).value;
			}
		}
	}

	public class LangTreeLeaf {
		public String value;
		public String key;

		//done
		public LangTreeLeaf(String IVal, String IKey) {
			value = IVal;
			key = IKey;
		}

	}
}

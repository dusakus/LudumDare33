package dcode.games.uEngine2.translator;

import java.util.ArrayList;

/**
 * Created by dusakus on 09.03.15.
 */
public class Translator {
	private ArrayList<LangContainer> languages;
	private LangContainer targetLang;
	private String baseLang;

	public Translator(String IbaseLang) {
		baseLang = IbaseLang;
		languages = new ArrayList<LangContainer>();
		languages.add(new LangContainer(baseLang));
	}

	public void setLang(String langId) {
		for (LangContainer lc : languages) {
			if (lc.langId.equals(langId)) {
				targetLang = lc;
				return;
			}
		}
		setLang(baseLang);
	}

}

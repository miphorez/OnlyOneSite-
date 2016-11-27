package xml.preset;

import java.util.ArrayList;

import static utils.ConstantForAll.urlBookmarks;

public class PresetContent {
    private ArrayList<TContent> listPresetContent = new ArrayList<>();

    public PresetContent() {
        createListPresetContent();
    }

    private void createListPresetContent() {
        listPresetContent.add(new TContent(
                "https://ru.wikipedia.org",
                "wikipedia",
                ETContent.DOMEN_LINK,
                false));
        listPresetContent.add(new TContent(
                "https://translate.google.com",
                "translate.google",
                ETContent.HTML_LINK,
                false));
        listPresetContent.add(new TContent(
                urlBookmarks,
                "www.online-life.cc",
                ETContent.ONLINE_LIFE,
                false));
    }

    public ArrayList<TContent> getListPresetContent() {
        return listPresetContent;
    }
}

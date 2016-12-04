package xml.preset;

import java.util.ArrayList;

public class PresetContent {
    private ArrayList<TContent> listPresetContent = new ArrayList<>();

    public PresetContent() {
        createListPresetContent();
    }

    private void createListPresetContent() {
        listPresetContent.add(new TContent(
                "https://ru.wikipedia.org/",
                "wikipedia",
                ETContent.LINK_DOMAIN,
                false));
        listPresetContent.add(new TContent(
                "https://translate.google.com/",
                "translate.google",
                ETContent.LINK_HTML,
                false));
        listPresetContent.add(new TContent(
                "http://www.online-life.cc/favorites/",
                "www.online-life.cc",
                ETContent.LINK_ONLINELIFE,
                false));
    }

    public ArrayList<TContent> getListPresetContent() {
        return listPresetContent;
    }
}

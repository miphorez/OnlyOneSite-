package xml.preset;

import java.util.Objects;

import static utils.UtilsForAll.xorHexDataToStr;

public class TContent {
    private String link;
    private String name;
    private ETContent type;
    private boolean modeDel;

    TContent(String link, String name, ETContent type, boolean modeDel) {
        this.link = link;
        this.name = name;
        this.type = type;
        this.modeDel = modeDel;
    }

    public TContent(String link, String name, String type, String modeDel) {
        this.link = xorHexDataToStr(link);
        this.name = xorHexDataToStr(name);
        this.type = ETContent.getByStrType(xorHexDataToStr(type));
        this.modeDel = Objects.equals(modeDel, "1");
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public ETContent getType() {
        return type;
    }

    public String getModeDel() {
        return modeDel ? "1" : "0";
    }

    @Override
    public String toString() {
        return name +" | "+ link  +" | "+ type.name() +" | "+ modeDel;
    }
}

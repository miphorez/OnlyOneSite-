package xml.preset;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static utils.UtilsForAll.xorHexDataToStr;

public class TContent {
    private int id;
    private String link;
    private String name;
    private ETContent type;
    private boolean modeDel;

    TContent(int id, String link, String name, ETContent type, boolean modeDel) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.type = type;
        this.modeDel = modeDel;
    }

    public TContent(String link, String name, String type, String modeDel) {
        id = getRandomId();
        this.link = xorHexDataToStr(link);
        this.name = xorHexDataToStr(name);
        this.type = ETContent.getByStrType(xorHexDataToStr(type));
        this.modeDel = Objects.equals(modeDel, "1");
    }

    public TContent(String link, String name, String type) {
        id = getRandomId();
        this.link = link;
        this.name = name;
        this.type = ETContent.getByStrType(type);
        this.modeDel = true;
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
        return id +" | "+ name +" | "+ link  +" | "+ type.name() +" | "+ modeDel;
    }

    public static TContent getTContentByName(ArrayList<TContent> listTContent, String strName) {
        for (TContent tContent: listTContent) {
            if(Objects.equals(tContent.getName(), strName)) return tContent;
        }
        return null;
    }

    public int getRandomId() {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(0x7FFFFFFF);
    }

    public String getStrId() {
        return Integer.toString(id);
    }
}

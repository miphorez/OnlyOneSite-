package xml.preset;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static utils.UtilsForAll.strDecodeBase64;

public class TContent {
    private int id;
    private String link;
    private String name;
    private ETContent type;
    private boolean modeDel;
    private boolean modeAdmin;

    TContent(int id, String link, String name, ETContent type, boolean modeDel) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.type = type;
        this.modeDel = modeDel;
    }

    public TContent(String strId, String link, String name, String type, String modeDel) {
        id = Integer.parseInt(strId);
        this.link = strDecodeBase64(link);
        this.name = strDecodeBase64(name);
        this.type = ETContent.getByStrType(strDecodeBase64(type));
        this.modeDel = Objects.equals(modeDel, "1");
    }

    public TContent(String link, String name, String type) {
        id = getRandomId();
        this.link = link;
        this.name = name;
        this.type = ETContent.getByStrType(type);
        this.modeDel = true;
    }

    public TContent(String strId, String link, String name, String type, boolean modeDel, boolean modeAdmin) {
        id = Integer.parseInt(strId);
        this.link = link;
        this.name = name;
        this.type = ETContent.getByStrType(type);
        this.modeDel = modeDel;
        this.modeAdmin = modeAdmin;
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

    public static int getRandomId() {
        Random random = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt(0x7FFFFFFF);
    }

    public String getStrId() {
        return Integer.toString(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModeDel(boolean modeDel) {
        this.modeDel = modeDel;
    }

    public boolean isModeDel() {
        return modeDel;
    }

    public boolean getModeAdmin() {
        return modeAdmin;
    }
}

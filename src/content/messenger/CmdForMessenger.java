package content.messenger;

import static xml.preset.ETContent.*;

public enum CmdForMessenger {
    COMMAND_NONE(""),

    GOTO_LINK_DOMAIN("%"+LINK_DOMAIN),
    GOTO_LINK_HTML("%"+LINK_HTML),
    GOTO_LINK_ONLINELIFE("%"+LINK_ONLINELIFE),
    GOTO_LINK_ONLINEMULTFILMY("%"+LINK_ONLINEMULTFILMY),
    ;
    private String strVal = "";

    CmdForMessenger(String strVal) {
        this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }

    static public CmdForMessenger getType(String str) {
        for (CmdForMessenger typeData: CmdForMessenger.values()) {
            if (typeData.name().equals(str)) {
                return typeData;
            }
        }
        return COMMAND_NONE;
    }

    static public CmdForMessenger getTypeByStr(String str) {
        for (CmdForMessenger typeData: CmdForMessenger.values()) {
            if (typeData.getStrVal().equals(str)) {
                return typeData;
            }
        }
        return COMMAND_NONE;
    }
}

package content.messenger;

import xml.preset.ETContent;

public class ArgForMessenger {
    public String strIdContent;
    public String strLinkContent;
    public String strNameContent;
    public String strTypeContent = ETContent.LINK_NONE.name();
    public String strModeDel;
    public String strModeAdmin;

    public ArgForMessenger(String strIdContent,
                           String strLinkContent,
                           String strNameContent,
                           String strTypeContent,
                           String strModeDel, boolean boolModeAdmin) {
        this.strIdContent = strIdContent;
        this.strLinkContent = strLinkContent;
        this.strNameContent = strNameContent;
        this.strTypeContent = strTypeContent;
        this.strModeDel = strModeDel;
        this.strModeAdmin = boolModeAdmin ? "1" : "0";
    }

    public ArgForMessenger(ArgForMessenger arg) {
        strIdContent = arg.strIdContent;
        strLinkContent = arg.strLinkContent;
        strNameContent = arg.strNameContent;
        strTypeContent = arg.strTypeContent;
        strModeDel = arg.strModeDel;
        strModeAdmin = arg.strModeAdmin;
    }
}

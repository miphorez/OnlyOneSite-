package frm.password;

import java.util.Objects;

public enum EInsideCmd {
    NONE,
    PasswordField_Exit,
    PasswordField_Enter,
    TextField_Exit,
    TextField_Enter,
    ;

    public static EInsideCmd getCmdByStr(String strCmd){
        for (EInsideCmd insideCmd: values()) {
            if (Objects.equals(insideCmd.name(), strCmd)) return insideCmd;
        }
        return NONE;
    }
}

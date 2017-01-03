package frm.password;

import java.util.Objects;

public enum EInsideCmd {
    NONE,
    PasswordField_Exit,
    PasswordField_Enter,
    ;

    static EInsideCmd getCmdByStr(String strCmd){
        for (EInsideCmd insideCmd: values()) {
            if (Objects.equals(insideCmd.name(), strCmd)) return insideCmd;
        }
        return NONE;
    }
}

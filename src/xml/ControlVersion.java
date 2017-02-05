package xml;

import utils.ConstantForAll;

import java.util.logging.Logger;

class ControlVersion extends XMLSettings{

    ControlVersion(Logger logger) {
        super(logger);
    }

    boolean go() {
        String strVer = getAttr(null, "Ver", false);
        TVersion tVersion = new TVersion(strVer);
        logger.info("Версия файла настроек: " + tVersion.getStrValueVer());
        if (tVersion.isEqVerLo(ConstantForAll.PROGRAM_VERSION)) {
            logger.info("Ошибка! Версия программы ниже, чем версия файла настроек");
            return false;
        }
//        return tVersion.isEqVer(ConstantForAll.PROGRAM_VERSION) ||
//               !tVersion.isEqVerHi(ConstantForAll.PROGRAM_VERSION) ||
                return new XMLSettingsUpdate(logger).go();
    }
}

package xml;

import utils.ConstantForAll;

import java.util.logging.Logger;

class ControlVersion extends XMLSettings{

    ControlVersion(Logger logger) {
        super(logger);
    }

    boolean go() {
        TVersion tVersion = new TVersion(getAttr(null, "Ver", false));
        logger.info("Версия файла настроек: " + tVersion.getStrValueVer());
        if (tVersion.isEqVerLo(ConstantForAll.PROGRAM_VERSION)) {
            logger.info("Ошибка! Версия программы ниже, чем версия файла настроек");
            return false;
        }
        return tVersion.isEqVer(ConstantForAll.PROGRAM_VERSION) ||
               !tVersion.isEqVerHi(ConstantForAll.PROGRAM_VERSION) ||
                new XMLSettingsUpdate(logger).go();
    }
}

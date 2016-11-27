package xml;


class TVersion {
    private int[] intValueVer;

    TVersion(String strVersion) {
        intValueVer = getIntValueVer(strVersion);
    }

    private int isEqualVer(String strEqVer) {
        int[] intProgVer = getIntValueVer(strEqVer);
        for (int i = 0; i < 3; i++) {
            if (intValueVer[i]>intProgVer[i])return -1;
            if (intValueVer[i]<intProgVer[i])return 1;
        }
        return 0;
    }

    boolean isEqVerHi(String strEqVer){
        return isEqualVer(strEqVer) == 1;
    }

    boolean isEqVerLo(String strEqVer){
        return isEqualVer(strEqVer) == -1;
    }

    String getStrValueVer(){
        String result = "";
        for (int i = 0; i < 3; i++) {
            result += intValueVer[i] + ".";
        }
        return result;
    }

    private int[] getIntValueVer(String strVersion) {
        int[] intVer = new int[3];
        String iStr;
        int iFirst;
        int iNext = -1;
        for (int i = 0; i < 3; i++) {
            iFirst = iNext + 1;
            String strDelimeter = ".";
            iNext = strVersion.indexOf(strDelimeter, iFirst);
            if (iNext == -1) {
                iStr = strVersion.substring(iFirst, strVersion.length());
            } else {
                iStr = strVersion.substring(iFirst, iNext);
            }
            intVer[i] = Integer.parseInt(iStr);
        }
        return intVer;
    }

    boolean isEqVer(String strEqVer) {
        return isEqualVer(strEqVer) == 0;
    }
}

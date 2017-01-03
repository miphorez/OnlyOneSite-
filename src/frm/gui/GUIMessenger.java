package frm.gui;

import java.util.Observable;

class GUIMessenger extends Observable {

    void putCmd(String arg) {
        setChanged();
        notifyObservers(arg);
    }
}

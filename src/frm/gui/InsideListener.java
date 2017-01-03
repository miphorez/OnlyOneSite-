package frm.gui;

import java.util.Observable;
import java.util.Observer;

public abstract class InsideListener implements Observer {
    public abstract void update(Observable o, Object arg);
}

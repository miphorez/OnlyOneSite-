package content.messenger;

import java.util.Observable;

public class MessengerChangeContent extends Observable {

    public void putNewContent(ArgForMessenger arg) {
        setChanged();
        notifyObservers(arg);
    }
}


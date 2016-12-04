package main.listener;

import java.util.Observable;
import java.util.Observer;

public abstract class ListenerFromContent implements Observer {
    public abstract void update(Observable o, Object arg);
}

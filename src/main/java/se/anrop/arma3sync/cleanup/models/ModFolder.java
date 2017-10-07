package se.anrop.arma3sync.cleanup.models;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.apache.commons.io.FileUtils;

import java.util.LinkedList;
import java.util.List;

public class ModFolder implements Observable {

    private final List<InvalidationListener> listeners = new LinkedList<>();

    private final String name;
    private Long size;

    public ModFolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
        listeners.stream().forEach(listener -> {
            listener.invalidated(this);
        });
    }

    @Override
    public String toString() {
        if (size != null) {
            return String.format("%s (%s)", name, FileUtils.byteCountToDisplaySize(size));
        }

        return name;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
}

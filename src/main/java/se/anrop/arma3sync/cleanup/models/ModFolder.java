package se.anrop.arma3sync.cleanup.models;

public class ModFolder {

    private final String name;

    public ModFolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

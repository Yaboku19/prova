package it.unibo.tankBattle.controller.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.tankBattle.common.NextAndPrevious;
import it.unibo.tankBattle.controller.api.ObjectsManager;

/*import model.virus.Virus;
import model.virus.VirusData;
import model.virus.VirusDataList;
import model.virus.VirusFactory;
import model.virus.VirusFactoryImpl;
import view.VirusSetup;*/

public abstract class ObjectsManagerImpl<T> implements ObjectsManager<T> {

    //private final File folder = new File(System.getProperty("user.home"), ".TB");

    protected final File config;

    //private final String path;// = "config/tankConfig.xml";

    protected final Map<String, T> tankMap = new HashMap<String, T>();
    protected final List<String> keyOrder = new ArrayList<>();
    private int index = 0;

    /**
     * Constructor method.
     * 
     */
    public ObjectsManagerImpl(String path) {
        config = new File(path);
    }

    /**
     *
     */
    @Override
    abstract public void read();

    @Override
    public T getActual() {
        return tankMap.get(keyOrder.get(index));
    }
    @Override
    public void update(final NextAndPrevious delta) {
        index += delta.getDelta();
        if (index >= keyOrder.size()) {
            index = 0;
        }
        if (index < 0) {
            index = keyOrder.size() - 1;
        }
    }

}

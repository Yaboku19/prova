package it.unibo.tankbattle.controller.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.bind.JAXBException;

import it.unibo.tankbattle.common.NextAndPrevious;
import it.unibo.tankbattle.common.input.api.Command;
import it.unibo.tankbattle.controller.api.GameEngine;
import it.unibo.tankbattle.controller.api.ObjectsManager;
import it.unibo.tankbattle.controller.api.Player;
import it.unibo.tankbattle.controller.api.WorldEventListener;
import it.unibo.tankbattle.model.gameSetup.api.Data;
import it.unibo.tankbattle.model.gameSetup.api.DataList;
import it.unibo.tankbattle.model.gameSetup.impl.MapData;
import it.unibo.tankbattle.model.gameSetup.impl.MapDataList;
import it.unibo.tankbattle.model.gameSetup.impl.TankData;
import it.unibo.tankbattle.model.gameSetup.impl.TankDataList;
import it.unibo.tankbattle.model.gameState.api.GameState;
import it.unibo.tankbattle.model.gameState.impl.GameStateImpl;
import it.unibo.tankbattle.view.api.View;
/**
 * javadoc.
 */
public class BasicGameEngine implements GameEngine, WorldEventListener {
    private static final long PERIOD = 20;
    private final View view;
    private final GameState model;
    private final Queue<Command> commandQueue = new LinkedList<>();
    private Thread thread;
    private Boolean isOver = false;
    private Player firstPlayer;
    private Player secondPlayer;
    private ObjectsManager<TankData, TankDataList> tankFirstManager;
    private ObjectsManager<TankData, TankDataList> tankSecondManager;
    private ObjectsManager<MapData, MapDataList> mapManager;
    /**
     * javadoc.
     * @param view
     */
    public BasicGameEngine(final View view) {
        thread = new Thread(this);
        this.view = view;
        view.setController(this);
        model = new GameStateImpl(this);
        try {
            tankFirstManager = generateObjectManager(
            ClassLoader.getSystemResource("config/tankConfig.xml").toURI(), TankDataList.class);
            tankSecondManager = generateObjectManager(
            ClassLoader.getSystemResource("config/tankConfig.xml").toURI(), TankDataList.class);
            mapManager = generateObjectManager(
            ClassLoader.getSystemResource("config/mapConfig.xml").toURI(), MapDataList.class);
        } catch (JAXBException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private <T extends Data, C extends DataList<T>> ObjectsManager<T, C> generateObjectManager(
            final URI path, final Class<C> clas) throws JAXBException {
        return new ObjectsManagerImpl<T, C>(path, clas);
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void startGame() {
        firstPlayer = new HumanPlayer("ema", tankFirstManager.getActual());
        secondPlayer = new HumanPlayer("ricky", tankSecondManager.getActual());
        model.createWorld(firstPlayer, secondPlayer, mapManager.getActual());
        System.out.println("start game");
        thread.start();
        //initGame();
        /*
         * new instance of model
         */
    }

    private void loop() {
        this.isOver = false;
        long previousCycleStartTime = System.currentTimeMillis();
        while (!isOver) {
            final long currentCycleStartTime = System.currentTimeMillis();
            final long elapsed = currentCycleStartTime - previousCycleStartTime;
            processInput();
            update(elapsed);
            render();
            waitForNextFrame(currentCycleStartTime);
            previousCycleStartTime = currentCycleStartTime;
        }
        view.gameOver();
    }

    private void waitForNextFrame(final long cycleStartTime) {
        final long dt = System.currentTimeMillis() - cycleStartTime;
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    private void processInput() {
        if (commandQueue.size() > 0) {
            final var cmd = commandQueue.poll();
            cmd.execute(model);
        }
    }

    private void update(final double elapsed) {
        model.update(elapsed);
    }

    private void render() {
        view.render(model.getTankTrasform(firstPlayer), model.getTankTrasform(secondPlayer), 
            model.getWallsTrasform(), model.getBulletsTrasform(), model.getTankLife(firstPlayer),
            model.getTankLife(secondPlayer));
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void notifyCommand(final Command command) {
        commandQueue.add(command);
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void endGame(final Player player) {
        player.incScore();
        view.setWinner(player.getCode());
        this.isOver = true;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void run() {
        loop();
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void updateTankPlayer1(final NextAndPrevious delta) {
        tankFirstManager.update(delta);
        view.viewUpdateP1(tankFirstManager.getActual().getSpeed(), tankFirstManager.getActual().getDamage(),
            tankFirstManager.getActual().getLife(), tankFirstManager.getActual().getResource());
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void updateTankPlayer2(final NextAndPrevious delta) {
        tankSecondManager.update(delta);
        view.viewUpdateP2(tankSecondManager.getActual().getSpeed(), tankSecondManager.getActual().getDamage(),
            tankSecondManager.getActual().getLife(), tankSecondManager.getActual().getResource());
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void updateMap(final NextAndPrevious delta) {
        mapManager.update(delta);
        view.viewUpdateMap(mapManager.getActual().getResource());
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void setViewResources() {
        view.setResource(tankFirstManager.getActual().getResource(),
                tankSecondManager.getActual().getResource(),
                mapManager.getActual().getResource());
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void restart() {
        thread.interrupt();
        thread = new Thread(this);
        model.createWorld(firstPlayer, secondPlayer, mapManager.getActual());
        thread.start();
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void newStart() {
        thread.interrupt();
        thread = new Thread(this);
    }
}

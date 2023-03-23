package it.unibo.tankBattle.controller.impl;

import java.util.LinkedList;
import java.util.Queue;
import it.unibo.tankBattle.common.input.api.Command;
import it.unibo.tankBattle.controller.api.GameEngine;
import it.unibo.tankBattle.controller.api.ObjectsManager;
import it.unibo.tankBattle.controller.api.Player;
import it.unibo.tankBattle.controller.api.WorldEventListener;
import it.unibo.tankBattle.model.gameSetup.MapData;
import it.unibo.tankBattle.model.gameSetup.TankData;
import it.unibo.tankBattle.model.gameState.api.GameState;
import it.unibo.tankBattle.model.gameState.impl.GameStateImpl;
import it.unibo.tankBattle.view.api.View;
import it.unibo.tankBattle.view.impl.javafx.controller.ChooseMenu;

public class BasicGameEngine implements GameEngine, WorldEventListener {
    private long period = 20;
    private final View view;
    private final GameState model;
    private final Queue<Command> commandQueue = new LinkedList<>();
    private Thread thread;
    private Boolean isOver = false;
    private Player firstPlayer = null;
    private Player secondPlayer = null;
    private final ObjectsManager<TankData> tankFirstManager;
    private final ObjectsManager<TankData> tankSecondManager;
    private final ObjectsManager<MapData> mapManager;
    private final FactoryObjectManager factoryObjectsManager;
    private ChooseMenu settingsViewController = null;

    public BasicGameEngine(final View view) {
        thread = new Thread(this);
        this.view = view;
        view.setController(this);
        model = new GameStateImpl(this);
        factoryObjectsManager = new FactoryObjectManager();
        tankFirstManager = factoryObjectsManager.tankManager();
        tankSecondManager = factoryObjectsManager.tankManager();
        mapManager = factoryObjectsManager.MapManager();
        //objectsManager.readVirus();
    }

    @Override
    public void startGame() {
        firstPlayer = new HumanPlayer(1);
        secondPlayer = new HumanPlayer(2);
        model.createWorld(firstPlayer, secondPlayer);
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
        while(!isOver) {
            //System.out.println("loop");
            long currentCycleStartTime = System.currentTimeMillis();
			long elapsed = currentCycleStartTime - previousCycleStartTime;
            processInput();
            update(elapsed);
            render();
            waitForNextFrame(currentCycleStartTime);
			previousCycleStartTime = currentCycleStartTime;
        }
        view.gameOver();
    }

    private void waitForNextFrame(long cycleStartTime){
		long dt = System.currentTimeMillis() - cycleStartTime;
		if (dt < period){
			try {
				Thread.sleep(period - dt);
			} catch (Exception ex){
                System.out.println(ex.toString());
            }
		}
	}

    private void processInput() {
        //System.out.println("sono qua");
        if (commandQueue.size() > 0){
            var cmd = commandQueue.poll();
            cmd.execute(model);
        }
        /*for(var tank : model.getWorld().getTanks()){
            tank.updateInput();
        }*/
        //throw new UnsupportedOperationException("Unimplemented method 'processInput'");
    }

    private void update(double elapsed) {
        model.update(elapsed);
    }

    private void render() {
        view.render(model.getTankTrasform(firstPlayer), model.getTankTrasform(secondPlayer), model.getWallsTrasform(), model.getBulletsTrasform());
        /*view.drawBullet(null);
        view.drawTank(null);*/
    }

    @Override
    public void notifyCommand(Command command) {
        commandQueue.add(command);
        //System.out.println(commandQueue);
    }
    @Override
    public void endGame(final Player player) {
        player.incScore();
        this.isOver = true;
        view.gameOver();
    }

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public void run() {
        //startGame();
        System.out.println("aaa");
        loop();
    }

    public void setSettingsViewController(ChooseMenu settingsViewController){
        this.settingsViewController = settingsViewController;
    }

    @Override
    public void updateTankPlayer1() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTankPlayer1'");
    }

    @Override
    public void updateTankPlayer2() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTankPlayer2'");
    }

    @Override
    public void updateMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMap'");
    }
}

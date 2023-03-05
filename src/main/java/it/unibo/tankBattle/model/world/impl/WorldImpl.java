package it.unibo.tankBattle.model.world.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.tankBattle.model.world.api.World;
import it.unibo.tankBattle.common.P2d;
import it.unibo.tankBattle.common.Player;
import it.unibo.tankBattle.common.input.api.Directions;
import it.unibo.tankBattle.model.gameObject.api.GameObject;
import it.unibo.tankBattle.model.gameObject.impl.FactoryGameObject;
import it.unibo.tankBattle.model.gameState.api.GameState;

public class WorldImpl implements World {
    private final Set<GameObject> wallSet;
    private final Set<GameObject> bulletSet;
    private final GameObject tankPlayerOne;
    private final GameObject tankPlayerTwo;
    private final FactoryGameObject factoryGameObject;
    private final GameState gameState;
    private static final int MULTIPLIER_SPEED_SIMPLE_TANK = 2;

    protected WorldImpl(final Set<GameObject> wallSet, final GameObject tankOne,
            final GameObject tankTwo, final GameState gameState) {

        this.wallSet = new HashSet<>(wallSet);
        this.bulletSet = new HashSet<>();
        tankPlayerOne = tankOne;
        tankPlayerTwo = tankTwo;
        factoryGameObject = new FactoryGameObject();
        this.gameState = gameState;
    }

    @Override
    public void update() {
        getEntities().stream().forEach(g -> {
            g.update();
            removeDeadGameObject(g);
        });
    }

    @Override
    public void collision(P2d firstPosition, P2d secondPosition) {

        final GameObject firstGameObject = getGameObjectFromPosition(firstPosition);
        final GameObject secondGameObject = getGameObjectFromPosition(secondPosition);

        firstGameObject.hit(secondGameObject.getDamage());
        firstGameObject.resolveCollision(secondGameObject);

        secondGameObject.hit(firstGameObject.getDamage());
        secondGameObject.resolveCollision(firstGameObject);
    }

    private GameObject getGameObjectFromPosition (P2d position) {
        return getEntities()
            .stream()
            .filter(g -> g.getPosition().equals(position))
            .toList()
            .get(0);
    }

    private void removeDeadGameObject(GameObject gameObject) {

        if (!gameObject.isAlive()) {
            if (bulletSet.contains(gameObject)){
                bulletSet.remove(gameObject);
            } else if (bulletSet.contains(gameObject)) {
                bulletSet.remove(gameObject);
            } else if (tankPlayerOne == gameObject || tankPlayerOne == gameObject){
                gameState.isOver();
            } else {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public Set<GameObject> getEntities() {
        var entities = new HashSet<GameObject>();
        entities.addAll(wallSet);
        entities.addAll(bulletSet);
        entities.addAll(getTanks());
        return entities;
    }

    @Override
    public Set<GameObject> getWalls() {
        return wallSet;
    }

    @Override
    public Set<GameObject> getBullets() {
        return bulletSet;
    }

    @Override
    public Set<GameObject> getTanks() {
        return Set.of(tankPlayerOne, tankPlayerTwo);
    }

    @Override
    public void shot(Player player) {
        if(player == Player.PLAYER_UNO) {
            addBullet(tankPlayerOne);
        } else if (player == Player.PLAYER_DUE) {
            addBullet(tankPlayerTwo);
        }
    }

    private void addBullet(GameObject tank) {
        bulletSet.add(factoryGameObject.simpleBullet(tank.getMaxSpeed() * MULTIPLIER_SPEED_SIMPLE_TANK, tank));
    }

    @Override
    public void setDirection(Directions direction, Player player) {
        if (player == Player.PLAYER_UNO) {
            changeDirection(tankPlayerOne, direction);
        } else {
            changeDirection(tankPlayerTwo, direction);
        }
    }

    private void changeDirection(GameObject gameObject, Directions direction) {
        gameObject.setDirection(direction);
    }
}

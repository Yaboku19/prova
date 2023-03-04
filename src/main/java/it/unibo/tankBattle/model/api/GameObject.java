package it.unibo.tankBattle.model.api;

import it.unibo.tankBattle.common.input.api.Directions;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

public abstract class GameObject {

    private final int maxSpeed;
    private Point2D position;
    private int speed;
    private Directions direction;
    private BoundingBox hitBox;
    private int damage;
    private int lifePoints;

    public GameObject(int speed, Point2D startPos, int lifePoints, int damage) {
        this.maxSpeed = speed;
        this.position = startPos;
        this.lifePoints = lifePoints;
        this.damage = damage;
        this.direction = Directions.UP;
    }

    public Point2D getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

    public Directions getDirection() {
        return direction;
    }

    public BoundingBox getBoundingBox() {
        return hitBox;
    }

    public int getDamage() {
        return damage;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setDirection(Directions dir) {
        this.direction = dir;
    }

    public void hit(int damageReceive) {
        this.lifePoints = this.lifePoints - damageReceive; 
    }

    public void move() {
        this.speed = maxSpeed;
    }

    public void stop() {
        this.speed = 0;
    }
    
    public abstract boolean isAlive(GameObject obj);

    public abstract void update();
    
}

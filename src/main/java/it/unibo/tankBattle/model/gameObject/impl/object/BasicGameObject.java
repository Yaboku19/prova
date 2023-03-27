package it.unibo.tankBattle.model.gameObject.impl.object;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.tankBattle.common.P2d;
import it.unibo.tankBattle.common.Transform;
import it.unibo.tankBattle.common.input.api.Direction;
import it.unibo.tankBattle.model.gameObject.api.component.Component;
import it.unibo.tankBattle.model.gameObject.api.component.Movable;
import it.unibo.tankBattle.model.gameObject.api.object.GameObject;

public class BasicGameObject implements GameObject{

    private P2d position;
    private Direction direction;
    private final double length;
    private final double width;
    private List<Component> components = new LinkedList<>();


    public BasicGameObject(final Transform transform) {
        this.position = transform.getPosition();
        this.direction = transform.getDirection();
        this.length = transform.getLength();
        this.width = transform.getWidth();
    }

    @Override
    public void update(double time) {
        /*if(this.getComponent(Movable.class).isPresent()) {
            this.getComponent(Movable.class).get().update(time);
        }
        if(this.getComponent(Tank.class).isPresent()) {
            this.getComponent(Tank.class).get().update(time);
        }*/
        this.getComponents().forEach(comp -> comp.update(time));
    }

    @Override
    public List<Component> getComponents() {
        return this.components;    
    }

    @Override
    public <T extends Component> Optional<T> getComponent(final Class<T> component) {
        return this.components.stream()
                .filter(comp -> component.isInstance(comp))
                .map(comp -> component.cast(comp))
                .findFirst();    
    }

    @Override
    public GameObject addComponent(final Component component) {
        this.components.add(component);
        component.attachGameObject(this);
        return this;     
    }

    @Override
    public Transform getTransform() {
        return new Transform(position, direction, length, width);
    }
    
    @Override
    public void setPosition(final P2d pos) {
        this.position = pos;
    }

    @Override
    public void setDirection(final Direction dir) {
        if(!dir.equals(Direction.NONE)) {
            this.direction = dir;
        }
        if(this.getComponent(Movable.class).isPresent()) {
            this.getComponent(Movable.class).get().setMovingDirection(dir);
        }
    }
}

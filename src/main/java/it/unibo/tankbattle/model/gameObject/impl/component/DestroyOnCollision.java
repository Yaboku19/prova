package it.unibo.tankbattle.model.gameobject.impl.component;

import it.unibo.tankbattle.model.collision.api.CollisionListener;
import it.unibo.tankbattle.model.gameobject.api.component.AbstractComponent;
import it.unibo.tankbattle.model.gameobject.api.component.Health;
import it.unibo.tankbattle.model.gameobject.api.component.ObservableCollidable;
import it.unibo.tankbattle.model.gameobject.api.object.GameObject;
/**
 * javadoc.
 */
public class DestroyOnCollision extends AbstractComponent implements CollisionListener {
    /**
    * {@inheritDoc}
    */
    @Override
    public void update(final double time) {
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void gameObjectAttached(final GameObject object) {
        requireSiblingComponent(ObservableCollidable.class).addListener(this);
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void handleCollision(final GameObject self, final GameObject collidingObject) {
        requireSiblingComponent(Health.class).die();
    }
}

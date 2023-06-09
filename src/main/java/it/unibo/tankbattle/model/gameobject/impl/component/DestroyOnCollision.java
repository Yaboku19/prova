package it.unibo.tankbattle.model.gameobject.impl.component;

import it.unibo.tankbattle.model.gameobject.api.component.Health;
import it.unibo.tankbattle.model.gameobject.api.object.GameObject;

/**
 * Represents a particular {@link it.unibo.tankbattle.model.gameobject.api.component.Component Component}
 * that enables the attached {@link GameObject} to be destroyed after a collision.
 */
public class DestroyOnCollision extends CollisionHandlingComponent {

    /**
    * {@inheritDoc}
    */
    @Override
    public void handleCollision(final GameObject self, final GameObject collidingObject) {
        requireSiblingComponent(Health.class).die();
    }
}

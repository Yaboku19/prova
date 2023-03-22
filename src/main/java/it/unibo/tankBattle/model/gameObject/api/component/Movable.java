package it.unibo.tankBattle.model.gameObject.api.component;

import it.unibo.tankBattle.common.input.api.Direction;

public interface Movable extends Component {

    /**
     * 
     * @return the component speed
     */
    public double getSpeed();

    /**
     * 
     * @return the direction where the object has to move, {@value}Directions.NONE if is not moving
     */
    public Direction getMovingDirection();

    /**
     * 
     * @param dir the next direction
     */
    public void setMovingDirection(final Direction dir);
}

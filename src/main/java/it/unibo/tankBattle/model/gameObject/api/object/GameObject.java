package it.unibo.tankBattle.model.gameObject.api.object;

import java.util.Optional;
import java.util.Set;

import it.unibo.tankBattle.common.P2d;
import it.unibo.tankBattle.common.Transform;
import it.unibo.tankBattle.common.input.api.Direction;
import it.unibo.tankBattle.model.gameObject.api.component.Component;

public interface GameObject {
    
    public void update(double time);

    public Set<Component> getComponents();

    public <T extends Component> Optional<T> getComponent(final Class<T> component);

    public GameObject addComponent(final Component component);

    public Transform getTransform();

    //public void setTransform(Transform transform);
    
    public void setPosition(final P2d pos);

    public void setDirection(final Direction pos);
}

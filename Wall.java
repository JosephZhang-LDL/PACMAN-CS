import info.gridworld.actor.Actor;
import java.awt.*;

/**
 * Created by jonah on 5/30/18.
 */
public class Wall extends Actor {
    public Wall(){
        setColor(Color.MAGENTA);
    }

    /**
     * This class is literally just a marker so Pacman can't do anything illegal, and to hold the wall sprite
     * Overrides the act method so it does even more nothing that before
     */
    @Override
    public void act(){}
}

package projects.Pacman; /**
 * Created by jonah on 5/30/18.
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PacManBoardRunner {
    public static void main(String args[]){

        PacManBoard world = new PacManBoard();
        world.buildBoard();
        final Player newPlayer = new Player();
        world.add(new Location(7, 8), newPlayer);
        world.add(new Location(3, 4), new BigFood());
        world.add(new Location(3, 14), new BigFood());
        world.add(new Location(17, 17), new BigFood());
        world.add(new Location(17, 1), new BigFood());

        world.add(new Location(7, 9), new Blinky());
        world.add(new Location(9, 10), new Inky());
        world.add(new Location(9, 9), new Pinky());
        world.add(new Location(9, 8), new Clyde());

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent event) {
                String key = KeyStroke.getKeyStrokeForEvent(event)
                        .toString();


                Grid gr = newPlayer.getGrid();
                Location loc = newPlayer.getLocation();
                Location nextNorth = loc.getAdjacentLocation(Location.NORTH);
                Location nextWest = loc.getAdjacentLocation(Location.WEST);
                Location nextEast = loc.getAdjacentLocation(Location.EAST);
                Location nextSouth = loc.getAdjacentLocation(Location.SOUTH);

                Actor neighborNorth = (Actor)gr.get(nextNorth);
                Actor neighborWest = (Actor)gr.get(nextWest);
                Actor neighborEast = (Actor)gr.get(nextEast);
                Actor neighborSouth = (Actor)gr.get(nextSouth);

                if (key.equals("pressed UP") && !(neighborNorth instanceof Wall))
                    newPlayer.setDirection(0);
                if (key.equals("pressed RIGHT") && !(neighborEast instanceof Wall))
                    newPlayer.setDirection(90);
                if (key.equals("pressed DOWN") && !(neighborSouth instanceof Wall))
                    newPlayer.setDirection(180);
                if (key.equals("pressed LEFT") && !(neighborWest instanceof Wall))
                    newPlayer.setDirection(270);
                return true;
            }
        });
    }
}
package projects.Pacman;/*
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import projects.Pacman.Player;
import projects.boxBug.Jumper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This class runs a world that contains box bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class BoxBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper();
        final Player newPlayer = new Player();
        world.add(new Location(7, 8), newPlayer);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
                    public boolean dispatchKeyEvent(KeyEvent event) {
                        String key = KeyStroke.getKeyStrokeForEvent(event)
                                .toString();
                        if (key.equals("pressed UP"))
                            newPlayer.setDirection(0);
                        if (key.equals("pressed RIGHT"))
                            newPlayer.setDirection(90);
                        if (key.equals("pressed DOWN"))
                            newPlayer.setDirection(180);
                        if (key.equals("pressed LEFT"))
                            newPlayer.setDirection(270);
                        return true;
                    }
                });
        world.show();
    }
}
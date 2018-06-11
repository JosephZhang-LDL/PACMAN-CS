package projects.Pacman;

import info.gridworld.actor.Actor;

/**
 * Created by jonah on 6/6/18.
 */
public abstract class Food extends Actor{
    private int pointValue;
    public Food(int pointValue){
        this.pointValue = pointValue;
    }
    public abstract int giveFood();

    public int getPointValue(){
        return pointValue;
    }

    @Override
    public void act(){}
}

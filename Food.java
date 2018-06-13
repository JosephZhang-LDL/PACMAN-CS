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

    /**
     * Food just sits there and takes it, so this act method overides the normal spinning around like a dummy
     */
    @Override
    public void act(){}
}

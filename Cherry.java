package projects.Pacman;

import java.awt.*;

/**
 * Created by hongzhenzhang on 6/16/18.
 */
public class Cherry extends Food{
    public Cherry() {
        super(500);
        setColor(Color.RED);
    }

    public int giveFood(){
        removeSelfFromGrid();
        return super.getPointValue();
    }
}

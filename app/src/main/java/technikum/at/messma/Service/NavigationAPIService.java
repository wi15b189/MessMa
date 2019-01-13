package technikum.at.messma.Service;

import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;

public class NavigationAPIService {

    public List<AccessPoint> getAPs (){
        List<AccessPoint> temp = new LinkedList<>();
        return temp;
    }

    public List<Stand> getStands (){
        List<Stand> temp = new LinkedList<>();
        return temp;
    }

    public List<GridPoint> navigate (GridPoint destination, List<AccessPoint> accesPoints){
        List<GridPoint> temp = new LinkedList<GridPoint>();
        temp.add(new GridPoint("1","100","100"));
        temp.add(new GridPoint("2","100","200"));
        temp.add(new GridPoint("3","200","200"));
        return temp;
    }

}

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
        Stand st1 = new Stand(
                1,
                "Stand1",
                "Wir suchen dich",
                "logopfad",
                new GridPoint(
                        "a1",
                        400,
                        400,
                        null));
        Stand st2 = new Stand(
                2,
                "Stand2",
                "Wir suchen dich",
                "logopfad",
                new GridPoint(
                        "a2",
                        500,
                        400,
                        null));
        Stand st3 = new Stand(
                3,
                "Stand3",
                "Wir suchen dich",
                "logopfad",
                new GridPoint(
                        "a3",
                        600,
                        400,
                        null));
        Stand st4 = new Stand(
                4,
                "Stand4",
                "Wir suchen dich",
                "logopfad",
                new GridPoint(
                        "a4",
                        400,
                        800,
                        null));
        temp.add(st1);
        temp.add(st2);
        temp.add(st3);
        temp.add(st4);

        return temp;
    }

    public List<GridPoint> navigate (GridPoint destination, List<AccessPoint> accesPoints){
        List<GridPoint> temp = new LinkedList<GridPoint>();
        temp.add(new GridPoint("1",100,100));
        temp.add(new GridPoint("2",100,200));
        temp.add(new GridPoint("3",200,200));
        temp.add(destination);
        return temp;
    }

}

package technikum.at.messma.Entities;

import java.util.List;

public class GridPoint {
    private String idGridPoint;
    private int posX;
    private int posY;
    private List<AccessPoint> accessPoints;

    public GridPoint(String idGridPoint, int posX, int posY, List<AccessPoint> accessPoints) {
        this.idGridPoint = idGridPoint;
        this.posX = posX;
        this.posY = posY;
        this.accessPoints = accessPoints;
    }

    public GridPoint(String idGridPoint, int posX, int posY) {
        this.idGridPoint = idGridPoint;
        this.posX = posX;
        this.posY = posY;
    }

    public String getIdGridPoint() {
        return idGridPoint;
    }

    public void setIdGridPoint(String idGridPoint) {
        this.idGridPoint = idGridPoint;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public List<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(List<AccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
    }
}
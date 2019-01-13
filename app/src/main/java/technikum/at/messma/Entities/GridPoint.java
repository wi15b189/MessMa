package technikum.at.messma.Entities;

import java.util.List;

public class GridPoint {
    private String idGridPoint;
    private String posX;
    private String posY;
    private List<AccessPoint> accessPoints;

    public GridPoint(String idGridPoint, String posX, String posY, List<AccessPoint> accessPoints) {
        this.idGridPoint = idGridPoint;
        this.posX = posX;
        this.posY = posY;
        this.accessPoints = accessPoints;
    }

    public GridPoint(String idGridPoint, String posX, String posY) {
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

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public List<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(List<AccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
    }
}
package technikum.at.messma.Entities;

public class Stand {
    private int idStand;
    private String name;
    private String description;
    private String logo;
    private GridPoint gridPoint;

    public Stand(int idStand, String name, String description, String logo, GridPoint gridPoint) {
        this.idStand = idStand;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.gridPoint = gridPoint;
    }

    public int getIdStand() {
        return idStand;
    }

    public void setIdStand(int idStand) {
        this.idStand = idStand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public GridPoint getGridPoint() {
        return gridPoint;
    }

    public void setGridPoint(GridPoint gridPoint) {
        this.gridPoint = gridPoint;
    }
}

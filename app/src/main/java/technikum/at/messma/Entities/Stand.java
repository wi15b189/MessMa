package technikum.at.messma.Entities;

import technikum.at.messma.R;

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

    public int getLogoAsInt() {
        try {
            return Integer.parseInt(this.logo);
        } catch (Exception e) {
            return R.drawable.ic_wc_black_24dp;
        }

    }

    public int getBackgroundSource() {
        switch (idStand) {
            case 1:
                return R.drawable.ic_atos;
            case 2:
                return R.drawable.ic_bwin;
            case 3:
                return R.drawable.ic_ibm;
            case 4:
                return R.drawable.ic_oebb;
            case 5:
                return R.drawable.ic_tmobile_2;
            case 6:
                return R.drawable.ic_deloitte_2;
            case 7:
                return R.drawable.ic_lift;
            case 8:
                return R.drawable.ic_reception;
            default:
                return R.drawable.ic_wc_black_24dp;
        }
    }
}

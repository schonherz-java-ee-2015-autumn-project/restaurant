package hu.schonherz.restaurant.service.vo;

import java.util.Date;

/**
 * Created by tothd on 2015. 12. 28..
 */
public abstract class BaseVo {

    private Date recDate;

    private Date modDate;

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
}

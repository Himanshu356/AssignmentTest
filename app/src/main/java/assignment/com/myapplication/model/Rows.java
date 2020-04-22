package assignment.com.myapplication.model;

import java.io.Serializable;

/**
 * Created by SV0047 on 07-11-2019.
 */

public class Rows implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    private String title;
    private String description;
    private String imageHref;
}

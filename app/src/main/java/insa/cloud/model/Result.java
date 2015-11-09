
package insa.cloud.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("event")
    @Expose
    private Integer event;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("row")
    @Expose
    private Integer row;
    @SerializedName("column")
    @Expose
    private Integer column;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The event
     */
    public Integer getEvent() {
        return event;
    }

    /**
     * 
     * @param event
     *     The event
     */
    public void setEvent(Integer event) {
        this.event = event;
    }

    /**
     * 
     * @return
     *     The level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The row
     */
    public Integer getRow() {
        return row;
    }

    /**
     * 
     * @param row
     *     The row
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * 
     * @return
     *     The column
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * 
     * @param column
     *     The column
     */
    public void setColumn(Integer column) {
        this.column = column;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

}

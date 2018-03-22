package cityworld.sec04.fjarquellada.es.sec04cityworld.model;

import cityworld.sec04.fjarquellada.es.sec04cityworld.CustomApplication;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by francisco on 21/3/18.
 */

public class City extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private String image;
    private int ranking;
    private String description;

    public City(){
        this.id = CustomApplication.CITY_ID.incrementAndGet();
    }
    public City(String name, String description, String image, int ranking){
        this.id = CustomApplication.CITY_ID.incrementAndGet();
        this.name        = name;
        this.description = description;
        this.image       = image;
        this.ranking     = ranking;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

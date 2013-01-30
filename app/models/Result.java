package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
public class Result extends Model {

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    public Date startedDate;

    @Temporal(TemporalType.TIMESTAMP)
    public Date completedDate;

    public String calledBy;

    public int iterations;

    public String description;

    public Long value;

    public String actorID;

    public static List<Result> getResults(){
        return Result.find("from Result ORDER BY createdDate ASC").fetch();
    }

}

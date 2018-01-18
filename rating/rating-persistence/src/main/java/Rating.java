import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "rating")
@NamedQueries(value =
        {
                @NamedQuery(name = "Rating.getAll", query = "SELECT v FROM rating v")
        })
@UuidGenerator(name =  "idGenerator")
public class Rating {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "customer_id")
    private String customer_id;

    @Column(name= "movie_id")
    private String movie_id;

    @Column(name = "score")
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

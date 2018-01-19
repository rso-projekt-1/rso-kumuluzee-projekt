import org.eclipse.persistence.annotations.UuidGenerator;
import rso.project.Customer;

import javax.persistence.*;
import java.util.List;

@Entity(name = "friend")
@NamedQueries(value =
        {
                @NamedQuery(name = "Friend.getAll", query = "SELECT v FROM friend v")
        })
@UuidGenerator(name =  "idGenerator")
public class Friend {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name="customer_id")
    private String customer_id;

    @Column(name= "friend_ids")
    private List<String> friend_ids;

    public List<String> getFriend_ids() {
        return friend_ids;
    }

    public void setFriend_ids(List<String> friend_ids) {
        this.friend_ids = friend_ids;
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

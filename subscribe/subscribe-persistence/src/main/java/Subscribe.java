import org.eclipse.persistence.annotations.UuidGenerator;
import rso.project.Customer;

import javax.persistence.*;
import java.util.List;

@Entity(name = "subscribe")
@NamedQueries(value =
        {
                @NamedQuery(name = "Subscribe.getAll", query = "SELECT v FROM subscribe v")
        })
@UuidGenerator(name =  "idGenerator")
public class Subscribe {
    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;


    @Column(name= "channel_name")
    private String channel_name;

    @Column(name = "subscriber_ids")
    private List<String> subscriber_ids;

    @Transient
    private List<Customer> subscribers;


    public List<Customer> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Customer> subscribers) {
        this.subscribers = subscribers;
    }

    public List<String> getSubscriber_ids() {
        return subscriber_ids;
    }

    public void setSubscriber_ids(List<String> subscriber_ids) {
        this.subscriber_ids = subscriber_ids;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

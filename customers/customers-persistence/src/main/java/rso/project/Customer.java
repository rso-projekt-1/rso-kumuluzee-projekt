package rso.project;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "customer")
@NamedQueries(value =
        {
                @NamedQuery(name = "Customer.getAll", query = "SELECT c FROM customer c")
        })
@UuidGenerator(name =  "idGenerator")
public class Customer{

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    private String address;

    @Column(name = "date_of_birth")
    private Date date_of_birth;
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX", timezone="CET")

    @Transient
    private List<Order> orders;

    @Transient
    private List<Cart> carts;

    //getters,setters

    public String getId(){
        return id;
    }
    public void setId(String new_id){
        id = new_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> orders) {
        this.carts = carts;
    }
}

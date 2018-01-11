package rso.project;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

    @Entity(name = "carts")
    @NamedQueries(value =
            {
                    @NamedQuery(name = "Cart.getAll", query = "SELECT c FROM carts c"),
                    @NamedQuery(name = "Cart.findByCustomer", query = "SELECT c FROM carts c WHERE c.customerId = " + ":customerId")
            })
    @UuidGenerator(name =  "idGenerator")
    public class Cart{

        @Id
        @GeneratedValue(generator = "idGenerator")
        private String id;

        private String title;

        private String description;

        private Date submitted;

        @Column(name = "customer_id")
        private String customerId;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public Date getSubmitted() {
            return submitted;
        }

        public void setSubmitted(Date submitted) {
            this.submitted = submitted;
        }
}

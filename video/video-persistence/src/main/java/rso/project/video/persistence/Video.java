package rso.project.video.persistence;


import org.eclipse.persistence.annotations.UuidGenerator;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "video")
@NamedQueries(value =
        {
                @NamedQuery(name = "Video.getAll", query = "SELECT v FROM video v")
        })
@UuidGenerator(name =  "idGenerator")
public class Video {
        @Id
        @GeneratedValue(generator = "idGenerator")
        private String id;

        @Column(name = "title")
        private String title;

        @Column(name= "uploader_id")
        private String uploader_id;

        @Column(name = "year_created")
        private String year_created;


        @Column(name = "date_uploaded")
        private Date date_uploaded;
        //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX", timezone="CET")

        public String getId() {
                return id;
        }
        public void setId(String id){
                this.id = id;
        }

        public Date getDate_uploaded() {
                return date_uploaded;
        }


        public String getYear_created() {
                return year_created;
        }

        public void setYear_created(String year_created) {
                this.year_created = year_created;
        }

        public void setDate_uploaded(Date date_uploaded) {
                this.date_uploaded = date_uploaded;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getUploader_id() {
                return uploader_id;
        }

        public void setUploader_id(String uploader_id) {
                this.uploader_id = uploader_id;
        }
}

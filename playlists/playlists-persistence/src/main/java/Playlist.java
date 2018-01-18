import org.eclipse.persistence.annotations.UuidGenerator;
import rso.project.video.persistence.Video;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "playlist")
@NamedQueries(value =
        {
                @NamedQuery(name = "Playlist.getAll", query = "SELECT v FROM playlist v")
        })
@UuidGenerator(name =  "idGenerator")
public class Playlist {

        @Id
        @GeneratedValue(generator = "idGenerator")
        private String id;

        @Column(name = "customer_id")
        private String customer_id;

        @Column(name= "playlist_name")
        private String playlist_name;

        @Column(name = "video_ids")
        private List<String> video_ids;

        @Transient
        private List<Video> films;


        //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX", timezone="CET")

        public String getId() {
            return id;
        }
        public void setId(String id){
            this.id = id;
        }


        public List<String> getVideo_ids() {
                return video_ids;
        }

        public void setVideo_ids(List<String> video_ids) {
                this.video_ids = video_ids;
        }

        public String getPlaylist_name() {
                return playlist_name;
        }

        public void setPlaylist_name(String playlist_name) {
                this.playlist_name = playlist_name;
        }

        public String getCustomer_id() {
                return customer_id;
        }

        public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
        }

        public List<Video> getFilms() {
                return films;
        }

        public void setFilms(List<Video> films) {
                this.films = films;
        }
}




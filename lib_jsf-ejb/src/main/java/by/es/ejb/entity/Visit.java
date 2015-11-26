package by.es.ejb.entity;


import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = "uniqueVisitorCount",
                query = "SELECT count(v) FROM Visit v WHERE v.lastLoginDate > :date")
})
@Entity
@Table(name = "VSIST")
public class Visit {

    @Id
    @Column(name = "HOST_IP" ,nullable = false)
    private String hostIP;

    @Column(name = "LAST_LOGIN_DATE")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLoginDate;


    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}

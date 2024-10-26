package finance.uc_project.model.chefs;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizational_chart")
public class organizationalChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrganizationalChart;

    @Column(name = "createdate", nullable = false)
    private LocalDateTime createdate;

    @Column(name = "updatedate", nullable = true)
    private LocalDateTime updatedate;

    @Column(name = "addby", nullable = false)
    private String addby;

    @Column(name = "updateby", nullable = true)
    private String updateby;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "filetype", nullable = false)
    private String filetype;

    @PrePersist
    protected void onCreate() {
        createdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedate = LocalDateTime.now();
    }
    
    // getters and setters
    public Long getIdOrganizationalChart() {
        return idOrganizationalChart;
    }

    public void setIdOrganizationalChart(Long idOrganizationalChart) {
        this.idOrganizationalChart = idOrganizationalChart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }

    public LocalDateTime getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(LocalDateTime updatedate) {
        this.updatedate = updatedate;
    }

    public String getAddby() {
        return addby;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }
    
    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    
}
    

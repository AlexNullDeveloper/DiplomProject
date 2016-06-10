package diplom.importexport;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by a.talismanov on 10.06.2016.
 */
@Entity
@Table(name = "import")
public class Import {

    @Id
    @Column(name = "IMP_ID")
    @GeneratedValue(generator = "my_seqence")
    @SequenceGenerator(name="my_seqence", sequenceName = "IMPORT_SEQ",allocationSize = 1)
    private long id;

    @Column(name = "TABLE_TO")
    private String tableTo;

    @Column(name = "IMP_FORMAT")
    private String impFormat;

    @Column(name = "IMP_DOC")
    private String impDoc;

    @Column(name = "IMP_DATE")
    private Date impDate;

    @Column(name = "IMP_USER")
    private String user;

    public Import() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public BigInteger getId() {
//        return id;
//    }
//
//    public void setId(BigInteger id) {
//        this.id = id;
//    }

    public String getTableTo() {
        return tableTo;
    }

    public void setTableTo(String tableTo) {
        this.tableTo = tableTo;
    }

    public String getImpFormat() {
        return impFormat;
    }

    public void setImpFormat(String impFormat) {
        this.impFormat = impFormat;
    }

    public String getImpDoc() {
        return impDoc;
    }

    public void setImpDoc(String impDoc) {
        this.impDoc = impDoc;
    }

    public Date getImpDate() {
        return impDate;
    }

    public void setImpDate(Date impDate) {
        this.impDate = impDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Import{" +
                "id=" + id +
                ", tableTo='" + tableTo + '\'' +
                ", impFormat='" + impFormat + '\'' +
                ", impDoc='" + impDoc + '\'' +
                ", impDate=" + impDate +
                ", user='" + user + '\'' +
                '}';
    }
}

package diplom.importexport;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by a.talismanov on 03.06.2016.
 */
@Entity
@Table(name="EXPORT")
public class Export {
    @Id @Column(name = "EXP_ID")
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(name="my_seq", sequenceName = "EXPORT_SEQ",allocationSize = 1)
    private int expId;

    @Column(name = "TABLE_FROM")
    private String tableFrom;

    @Column(name = "EXP_FORMAT")
    private String expFormat;

    @Column(name = "EXP_RESULT")
    private String expResult;

    @Column(name = "EXP_DATE")
    private java.sql.Date expDate;

    @Column(name = "EXP_USER")
    private String expUser;

    public String getExpUser() {
        return expUser;
    }

    public void setExpUser(String expUser) {
        this.expUser = expUser;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getTableFrom() {
        return tableFrom;
    }

    public void setTableFrom(String tableFrom) {
        this.tableFrom = tableFrom;
    }

    public String getExpFormat() {
        return expFormat;
    }

    public void setExpFormat(String expFormat) {
        this.expFormat = expFormat;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getExpResult() {
        return expResult;
    }

    public void setExpResult(String expResult) {
        this.expResult = expResult;
    }


}

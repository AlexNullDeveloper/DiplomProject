package diplom.importexport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by a.talismanov on 03.06.2016.
 */
@Entity
@Table(name = "TRN")
@XmlRootElement(name = "transaction")
public class Trn {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id @Column(name = "id")
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(name="my_seq", sequenceName = "TRN_SEQ",allocationSize = 1)

    private long id;
    @Column(name = "dognum")
    private int dognum;

    @Column(name = "date_success")
    private Date dateSuccess;

    @Column(name = "acc_deb")
    private String accDeb;

    @Column(name = "cur_deb")
    private String curDeb;

    @Column(name = "acc_cred")
    private String accCred;

    @Column(name = "cur_cred")
    private String curCred;

    @Column(name = "sum_deb")
    private BigDecimal sumDeb;

    @Column(name = "sum_cred")
    private BigDecimal sumCred;

    public Trn() {}


    public BigDecimal getSumCred() {
        return sumCred;
    }

    public void setSumCred(BigDecimal sumCred) {
        this.sumCred = sumCred;
    }

    public BigDecimal getSumDeb() {
        return sumDeb;
    }

    public void setSumDeb(BigDecimal sumDeb) {
        this.sumDeb = sumDeb;
    }

    public String getCurCred() {
        return curCred;
    }

    public void setCurCred(String curCred) {
        this.curCred = curCred;
    }

    public String getAccCred() {
        return accCred;
    }

    public void setAccCred(String accCred) {
        this.accCred = accCred;
    }

    public String getCurDeb() {
        return curDeb;
    }

    public void setCurDeb(String curDeb) {
        this.curDeb = curDeb;
    }

    public String getAccDeb() {
        return accDeb;
    }

    public void setAccDeb(String accDeb) {
        this.accDeb = accDeb;
    }
    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getDateSuccess() {
        return dateSuccess;
    }

    public void setDateSuccess(Date dateSuccess) {
        this.dateSuccess = dateSuccess;
    }


    public int getDognum() {
        return dognum;
    }

    public void setDognum(int dognum) {
        this.dognum = dognum;
    }

    @Override
    public String toString() {
        return "Trn{" +
                "id=" + id +
                ", dognum=" + dognum +
                ", dateSuccess=" + dateSuccess +
                ", accDeb='" + accDeb + '\'' +
                ", curDeb='" + curDeb + '\'' +
                ", accCred='" + accCred + '\'' +
                ", curCred='" + curCred + '\'' +
                ", sumDeb=" + sumDeb +
                ", sumCred=" + sumCred +
                '}';
    }
}

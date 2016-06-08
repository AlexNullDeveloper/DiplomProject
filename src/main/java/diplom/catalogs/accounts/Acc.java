package diplom.catalogs.accounts;

import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

/**
 * @author a.talismanov
 * @version 1.0.0 08.06.2016.
 */
@Entity
@Table(name = "ACC")
public class Acc {
    @Id
    @Column(name = "ACC_ID")
    @GeneratedValue(generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "ACC_SEQ", allocationSize = 1)
    private String accId;
    @Column(name = "ACC_CUR")
    private String accCur;
    @Column(name = "NAME_OF_OWNER")
    private String nameOfOwner;
    @Column(name = "ENG_NAME_OF_OWNER")
    private String engNameOfOwner;
    @Column(name = "SHORT_NAME_OF_OWNER")
    private String shortNameOfOwner;
    @Column(name = "OTD")
    private BigInteger otd;
    @Column(name = "USER_REG")
    private String userReg;
    @Column(name = "USER_REG_DATE")
    private Date userRegDate;
    @Column(name = "USER_EDIT")
    private String userEdit;
    @Column(name = "USER_EDIT_DATE")
    private Date userEditDate;
    @Column(name = "CURRENT_OST")
    private BigDecimal currentOst;

    @Transient
    private SimpleIntegerProperty accIdProp;

    @Override
    public String toString() {
        return "Acc{" +
                "accId='" + accId + '\'' +
                ", accCur='" + accCur + '\'' +
                ", nameOfOwner='" + nameOfOwner + '\'' +
                ", engNameOfOwner='" + engNameOfOwner + '\'' +
                ", shortNameOfOwner='" + shortNameOfOwner + '\'' +
                ", otd=" + otd +
                ", userReg='" + userReg + '\'' +
                ", userRegDate=" + userRegDate +
                ", userEdit='" + userEdit + '\'' +
                ", userEditDate=" + userEditDate +
                ", currentOst=" + currentOst +
                '}';
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccCur() {
        return accCur;
    }

    public void setAccCur(String accCur) {
        this.accCur = accCur;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }

    public String getEngNameOfOwner() {
        return engNameOfOwner;
    }

    public void setEngNameOfOwner(String engNameOfOwner) {
        this.engNameOfOwner = engNameOfOwner;
    }

    public String getShortNameOfOwner() {
        return shortNameOfOwner;
    }

    public void setShortNameOfOwner(String shortNameOfOwner) {
        this.shortNameOfOwner = shortNameOfOwner;
    }

    public BigInteger getOtd() {
        return otd;
    }

    public void setOtd(BigInteger otd) {
        this.otd = otd;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    public Date getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(Date userRegDate) {
        this.userRegDate = userRegDate;
    }

    public String getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(String userEdit) {
        this.userEdit = userEdit;
    }

    public Date getUserEditDate() {
        return userEditDate;
    }

    public void setUserEditDate(Date userEditDate) {
        this.userEditDate = userEditDate;
    }

    public BigDecimal getCurrentOst() {
        return currentOst;
    }

    public void setCurrentOst(BigDecimal currentOst) {
        this.currentOst = currentOst;
    }

    public int getAccIdProp() {
        return accIdProp.get();
    }

    public SimpleIntegerProperty accIdPropProperty() {
        return accIdProp;
    }

    public void setAccIdProp(int accIdProp) {
        this.accIdProp.set(accIdProp);
    }
}

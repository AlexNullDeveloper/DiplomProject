package diplom.catalogs;

import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by a.talismanov on 06.06.2016.
 */
@Entity
@Table(name = "CUS")
public class Customer {
    @Id
    @Column(name = "CUS_ID")
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(name="my_seq", sequenceName = "CUS_SEQ",allocationSize = 1)
    private BigInteger cusId;

    public Customer(BigInteger bigInteger){
        this.cusId = bigInteger;
        String s = bigInteger + "";
        this.cusIdProp = new SimpleIntegerProperty(Integer.parseInt(s));
    }

    public Customer(){}

    public Customer(BigInteger cusId, String cusName, String cusEngName, String cusShortName,
                    String cusOgrn, String regOrg, Date dateReg, String placeReg, String passport,
                    String cusInn, String numSvid, String nalInspec, String cusKpp, String userReg,
                    Date userRegDate, String userEdit, Date userEditDate, String email, BigInteger adressId) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusEngName = cusEngName;
        this.cusShortName = cusShortName;
        this.cusOgrn = cusOgrn;
        this.regOrg = regOrg;
        this.dateReg = dateReg;
        this.placeReg = placeReg;
        this.passport = passport;
        this.cusInn = cusInn;
        this.numSvid = numSvid;
        this.nalInspec = nalInspec;
        this.cusKpp = cusKpp;
        this.userReg = userReg;
        this.userRegDate = userRegDate;
        this.userEdit = userEdit;
        this.userEditDate = userEditDate;
        this.email = email;
        this.adressId = adressId;
    }


    public int getCusIdProp() {
        return cusIdProp.get();
    }

    public SimpleIntegerProperty cusIdPropProperty() {
        return cusIdProp;
    }

    public void setCusIdProp(int cusIdProp) {
        this.cusIdProp.set(cusIdProp);
    }

    @Transient
    private SimpleIntegerProperty cusIdProp;

    @Column(name = "cus_name")
    private String cusName;

    @Column(name = "cus_eng_name")
    private String cusEngName;

    @Column(name = "cus_short_name")
    private String cusShortName;

    @Column(name = "cus_ogrn")
    private String cusOgrn;

    @Column(name = "reg_org")
    private String regOrg;

    @Column(name = "passport")
    private String passport;

    @Column(name = "date_reg")
    private Date dateReg;

    @Column(name = "place_reg")
    private String placeReg;

    @Column(name = "cus_inn")
    private String cusInn;

    @Column(name = "cus_kpp")
    private String cusKpp;

    @Column(name = "NUM_SVID")
    private String numSvid;

    @Column(name = "NAL_INSPEK")
    private String nalInspec;

    @Column(name = "USER_REG")
    private String userReg;

    @Column(name = "USER_REG_DATE")
    private Date userRegDate;

    @Column(name = "USER_EDIT")
    private String userEdit;

    @Column(name = "USER_EDIT_DATE")
    private Date userEditDate;

    @Column(name = "E_MAIL")
    private String email;

    @Column(name = "ADRESS_ID")
    private BigInteger adressId;

    public BigInteger getAdressId() {
        return adressId;
    }

    public void setAdressId(BigInteger adressId) {
        this.adressId = adressId;
    }

    public BigInteger getCusId() {
        return cusId;
    }

    public void setCusId(BigInteger cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusEngName() {
        return cusEngName;
    }

    public void setCusEngName(String cusEngName) {
        this.cusEngName = cusEngName;
    }

    public String getCusShortName() {
        return cusShortName;
    }

    public void setCusShortName(String cusShortName) {
        this.cusShortName = cusShortName;
    }

    public String getCusOgrn() {
        return cusOgrn;
    }

    public void setCusOgrn(String cusOgrn) {
        this.cusOgrn = cusOgrn;
    }

    public String getRegOrg() {
        return regOrg;
    }

    public void setRegOrg(String regOrg) {
        this.regOrg = regOrg;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public String getPlaceReg() {
        return placeReg;
    }

    public void setPlaceReg(String placeReg) {
        this.placeReg = placeReg;
    }

    public String getCusInn() {
        return cusInn;
    }

    public void setCusInn(String cusInn) {
        this.cusInn = cusInn;
    }

    public String getCusKpp() {
        return cusKpp;
    }

    public void setCusKpp(String cusKpp) {
        this.cusKpp = cusKpp;
    }

    public String getNumSvid() {
        return numSvid;
    }

    public void setNumSvid(String numSvid) {
        this.numSvid = numSvid;
    }

    public String getNalInspec() {
        return nalInspec;
    }

    public void setNalInspec(String nalInspec) {
        this.nalInspec = nalInspec;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId=" + cusId +
                ", cusIdProp=" + cusIdProp +
                ", cusName='" + cusName + '\'' +
                ", cusEngName='" + cusEngName + '\'' +
                ", cusShortName='" + cusShortName + '\'' +
                ", cusOgrn='" + cusOgrn + '\'' +
                ", regOrg='" + regOrg + '\'' +
                ", passport='" + passport + '\'' +
                ", dateReg=" + dateReg +
                ", placeReg='" + placeReg + '\'' +
                ", cusInn='" + cusInn + '\'' +
                ", cusKpp='" + cusKpp + '\'' +
                ", numSvid='" + numSvid + '\'' +
                ", nalInspec='" + nalInspec + '\'' +
                ", userReg='" + userReg + '\'' +
                ", userRegDate=" + userRegDate +
                ", userEdit='" + userEdit + '\'' +
                ", userEditDate=" + userEditDate +
                ", email='" + email + '\'' +
                ", adressId=" + adressId +
                '}';
    }
}

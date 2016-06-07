package diplom.catalogs;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by a.talismanov on 06.06.2016.
 */
@Entity
@Table(name = "ADRESS")

public class AdressJpa {
    @Id @Column(name = "adr_id")
    @GeneratedValue(generator = "my_seq")
    @SequenceGenerator(name = "my_seq",sequenceName = "ADDRESS_SEQ",allocationSize = 1)
    private BigInteger id;
    @Column(name = "fiz_adr")
    private String fizAdr;
    @Column(name = "ur_adr")
    private String urAdr;
    @Column(name = "pocht_adr")
    private String pochtAdr;

    public AdressJpa() {
    }

    public String getPochtAdr() {
        return pochtAdr;
    }

    public void setPochtAdr(String pochtAdr) {
        this.pochtAdr = pochtAdr;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFizAdr() {
        return fizAdr;
    }

    public void setFizAdr(String fizAdr) {
        this.fizAdr = fizAdr;
    }

    public String getUrAdr() {
        return urAdr;
    }

    public void setUrAdr(String urAdr) {
        this.urAdr = urAdr;
    }

    @Override
    public String toString() {
        return "AdressJpa{" +
                "id=" + id +
                ", fizAdr='" + fizAdr + '\'' +
                ", urAdr='" + urAdr + '\'' +
                ", pochtAdr='" + pochtAdr + '\'' +
                '}';
    }
}

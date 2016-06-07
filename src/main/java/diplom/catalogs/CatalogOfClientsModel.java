package diplom.catalogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by a.talismanov on 06.06.2016.
 */
public class CatalogOfClientsModel {
    private static SessionFactory factory;

    void getCustomersFromDB() {
        try {
            factory = new AnnotationConfiguration().configure().addPackage("catalogs").addAnnotatedClass(Customer.class).buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("не создался SessionFactory " + th);
        }

        CatalogOfClientsModel catalogOfClientsModel = new CatalogOfClientsModel();
        catalogOfClientsModel.populateCatalogOfClients();

    }

    private void populateCatalogOfClients() {

        Session session = factory.openSession();

        String hql = "SELECT C.cusId FROM diplom.catalogs.Customer C";

        Query query = session.createQuery(hql);

        List<BigInteger> result = (List<BigInteger>) query.list();
//        for(BigInteger itm : result){
//            System.out.println(itm);
//        }

//        CatalogOfClients catalogOfClients =  new CatalogOfClients();
        ObservableList<BigInteger> ClientIds = FXCollections.observableList(result);
        CatalogOfClients.setNumsOfClients(ClientIds);

    }

    public Customer getCustomerDetailsFromDB(BigInteger selectedId) {
        Session session = factory.openSession();
        String hql = "FROM diplom.catalogs.Customer C where C.cusId = :customer_id";
        Query query = session.createQuery(hql);

        // BigInteger cusId = getIntFromBigInteger(selectedId);

        Customer resultCustomer = null;

        query.setParameter("customer_id", selectedId);
        List<Customer> customerDetails = (List<Customer>) query.list();
        for (Customer cust : customerDetails)
            resultCustomer = cust;

        return resultCustomer;
    }

    public void updateClientDetails(String editDate, String fullName, String engName, String shortName, String ogrn,
                                    String regOrg, String passport, String dateReg, String placeReg, String inn,
                                    String kpp, String numSvid, String nalInsp, String email,
                                    String userReg, String dateZav, String userEdit) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            String hql = "UPDATE diplom.catalogs.Customer C SET C.userEditDate = :date_edit," +
//                    "C.cusName = :full_name, C.cusEngName = :eng_name, C.cusShortName = :short_name," +
//                    "C.cusOgrn = :ogrn, C.regOrg = :reg_org, C.passport = :passport, C.dateReg = :date_reg," +
//                    "C.placeReg = :place_reg, C.cusInn = :inn, C.cusKpp = :kpp, C.numSvid = :num_svid, " +
//                    "C.nalInspec = :nal_inspec, C.email = :e_mail, C.userReg = :user_reg," +
//                    " C.userRegDate = :date_zav, C.userEdit = :user_edit" +
//                    "WHERE C.cusId = :id";

            String hql = "UPDATE diplom.catalogs.Customer SET userEditDate = :date_edit, " +
                    "cusName = :full_name, cusEngName = :eng_name, cusShortName = :short_name, " +
                    "cusOgrn = :ogrn, regOrg = :reg_org, passport = :passport, dateReg = :date_reg, " +
                    "placeReg = :place_reg, cusInn = :inn, cusKpp = :kpp, numSvid = :num_svid, " +
                    "nalInspec = :nal_inspec, email = :e_mail, userReg = :user_reg, " +
                    " userRegDate = :date_zav, userEdit = :user_edit " +
                    "WHERE cusId = :id";
            Query query = session.createQuery(hql);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date dateEdit = null;
            try {
                dateEdit = new java.sql.Date(sdf.parse(editDate).getTime());
            } catch (ParseException e){
                dateEdit = null;
            }


            query.setParameter("date_edit", dateEdit);
            query.setParameter("full_name",fullName);
            query.setParameter("eng_name",engName);
            query.setParameter("short_name",shortName);
            query.setParameter("ogrn",ogrn);
            query.setParameter("reg_org",regOrg);
            query.setParameter("passport",passport);

            java.sql.Date dateRegistrirovania = null;
            java.util.Date tempDate = sdf.parse(dateReg);
            dateRegistrirovania = new java.sql.Date(tempDate.getTime());


            query.setParameter("date_reg",dateRegistrirovania);

            query.setParameter("place_reg",placeReg);
            query.setParameter("inn",inn);
            query.setParameter("kpp",kpp);
            query.setParameter("num_svid",numSvid);
            query.setParameter("nal_inspec",nalInsp);
            query.setParameter("e_mail",email);
            query.setParameter("user_reg",userReg);

            java.sql.Date dateZavedenia = null;
            tempDate = sdf.parse(dateZav);
            dateZavedenia = new java.sql.Date(tempDate.getTime());


            query.setParameter("date_zav",dateZavedenia);
            query.setParameter("user_edit",userEdit);

            query.setParameter("id",CatalogOfClients.getSelectedId());


            int result = query.executeUpdate();
            System.out.println("Rows updated: " + result);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } catch (ParseException e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

//    private BigInteger getIntFromBigInteger(BigInteger selectedId) {
//        BigInteger result = new BigInteger(selectedId + "");
//        return result;
//    }
}

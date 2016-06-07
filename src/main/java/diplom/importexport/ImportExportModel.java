package diplom.importexport;

import com.google.gson.Gson;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Created by a.talismanov on 03.06.2016.
 */
public class ImportExportModel {
    private static SessionFactory factory;
    private String username;
    private String inFormat;
    private String tableFrom;
    private Date dateWhen;


    static Logger log = Logger.getLogger(ImportExportModel.class.getName());

    public void exportFromDB() {
        Locale.setDefault(new Locale("en", "US"));

        try {
            factory = new AnnotationConfiguration().
                    configure().
                    addPackage("importexport").
                    addAnnotatedClass(Export.class).
                    buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Не получилось создать sessionFactory object" + th);
        }


        ImportExportModel importExportModel = new ImportExportModel();

        initExportComponents(importExportModel);

        getSysdate(importExportModel);

        importExportModel.getUserName();

//        System.out.println(importExportModel.tableFrom);
//        System.out.println(importExportModel.inFormat);
//        System.out.println(importExportModel.dateWhen);


        if (importExportModel.inFormat.equals("CSV")) {
            exportDataInCSV(importExportModel.tableFrom);
        } else if (importExportModel.inFormat.equals("JSON")) {
            exportDataInJSON(importExportModel.tableFrom);
        } else if (importExportModel.inFormat.equals("XML")) {
            exportDataInXML(importExportModel.tableFrom);
        }


        Integer exportID = importExportModel.addExport(importExportModel.tableFrom, importExportModel.inFormat,
                "raz;dva;tri;", importExportModel.dateWhen, importExportModel.username);

    }

    private void exportDataInXML(String tableFrom) {
        System.out.println("exportDataInXML");

        Session session = factory.openSession();
        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());
            final String exportPath = "C:\\inout\\export\\xml";
            File exportImportDir = new File(exportPath);
            if (!exportImportDir.exists()) {
                System.out.println("creating " + exportPath);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                if (result)
                    System.out.println("directory created");

            }

            DocumentBuilderFactory documentBuilderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder =
                        documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document document = documentBuilder.newDocument();
            String root = "transactions";
            Element rootElement = document.createElement(root);
            document.appendChild(rootElement);


            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportPath + "\\" + tableFrom + ".xml"), "utf-8"));
                Trns trns = new Trns();
                for (Trn trn : results) {
                    String stringTransaction = "transaction";
                    Element elementTransaction = document.createElement(stringTransaction);
                    rootElement.appendChild(elementTransaction);


                    String idStr = "id";
                    Element elementId = document.createElement(idStr);
                    elementTransaction.appendChild(elementId);
                    elementId.appendChild(document.createTextNode(trn.getId().toString()));

                    String dognumStr = "dognum";
                    Element elementDocnum = document.createElement(dognumStr);
                    elementTransaction.appendChild(elementDocnum);
                    elementDocnum.appendChild(document.createTextNode(((Integer) trn.getDognum()).toString()));

                    String dateSuccessStr = "dateSuccess";
                    Element elementDateSuccess = document.createElement(dateSuccessStr);
                    elementTransaction.appendChild(elementDateSuccess);
                    elementDateSuccess.appendChild(document.createTextNode(trn.getDateSuccess().toString()));


                    String accDebStr = "accDeb";
                    Element elementAccDeb = document.createElement(accDebStr);
                    elementTransaction.appendChild(elementAccDeb);
                    elementAccDeb.appendChild(document.createTextNode(trn.getAccDeb()));


                    String curDebStr = "curDeb";
                    Element elementCurDeb = document.createElement(curDebStr);
                    elementTransaction.appendChild(elementCurDeb);
                    elementCurDeb.appendChild(document.createTextNode(trn.getCurDeb()));

                    String accCredStr = "accCred";
                    Element elementaccCred = document.createElement(accCredStr);
                    elementTransaction.appendChild(elementaccCred);
                    elementaccCred.appendChild(document.createTextNode(trn.getAccCred()));

                    String curCredStr = "curCred";
                    Element elementCurCred = document.createElement(curCredStr);
                    elementTransaction.appendChild(elementCurCred);
                    elementCurCred.appendChild(document.createTextNode(trn.getCurCred().toString()));

                    String sumDebStr = "sumDeb";
                    Element elementSumDeb = document.createElement(sumDebStr);
                    elementTransaction.appendChild(elementSumDeb);
                    elementSumDeb.appendChild(document.createTextNode(trn.getSumDeb().toString()));

                    String sumCredStr = "sumCred";
                    Element elementSumCred = document.createElement(sumCredStr);
                    elementTransaction.appendChild(elementSumCred);
                    elementSumCred.appendChild(document.createTextNode(trn.getSumCred().toString()));
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                try {
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(writer);
                    transformer.transform(source, result);
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }


                System.out.println("before commit;");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private void exportDataInJSON(String tableFrom) {
        Session session = factory.openSession();


        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());

            final String exportPath = "C:\\inout\\export\\json";
            File exportImportDir = new File(exportPath);
            if (!exportImportDir.exists()) {
                System.out.println("creating " + exportPath);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                if (result)
                    System.out.println("directory created");

            }

            Gson gson = new Gson();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportPath + "\\" + tableFrom + ".json"), "utf-8"));
                Trns trns = new Trns();
                for (Trn trn : results) {
                    // System.out.println("row " + object);
                    //gson.toJson(trn,writer);
                    trns.getListOfTrn().add(trn);
                }
                gson.toJson(trns, writer);
                System.out.println("before commit;");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    private void exportDataInCSV(String tableFrom) {
        System.out.println("exportDataInCSV");
        Session session = factory.openSession();


        try {
            String hql = "FROM diplom.importexport.Trn";
            Query query = session.createQuery(hql);

            List<Trn> results = (List<Trn>) query.list();
            System.out.println("List to string " + results.toString());


            final String exportCSV = "C:\\inout\\export\\csv";
            File exportImportDir = new File(exportCSV);
            if (!exportImportDir.exists()) {
                System.out.println("creating " + exportCSV);

                boolean result = false;

                try {
                    exportImportDir.mkdirs();
                    result = true;
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                if (result)
                    System.out.println("directory created");
            }


            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(exportCSV + "\\" + tableFrom + ".csv"), "utf-8"));
                for (Trn trn : results) {
                    writer.write(((BigInteger) trn.getId()).toString());
                    writer.append(';');
                    writer.write(((Integer) trn.getDognum()).toString());
                    writer.append(';');
                    writer.write(trn.getDateSuccess().toString());
                    writer.append(';');
                    writer.append(trn.getAccDeb());
                    writer.append(';');
                    writer.append(trn.getCurDeb());
                    writer.append(';');
                    writer.append(trn.getAccCred());
                    writer.append(';');
                    writer.append(trn.getCurCred());
                    writer.append(';');
                    writer.append(trn.getSumDeb().toString());
                    writer.append(';');
                    writer.append(trn.getSumCred().toString());
                    writer.append(';');

                    writer.newLine();
                }
                System.out.println("before commit;");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    private void getSysdate(ImportExportModel importExportModel) {
        Session session = factory.openSession();
        String sql = "SELECT SYSDATE FROM DUAL";
        SQLQuery query = session.createSQLQuery(sql);

        List<Object> result = (List<Object>) query.list();

        for (Object obj : result) {
            importExportModel.dateWhen = new Date(((Timestamp) obj).getTime());
        }

    }

    private void initExportComponents(ImportExportModel importExportModel) {

        ToggleGroup group = ImportExportFrame.getGroupInFormat();

        if (group.getSelectedToggle() != null) {
            importExportModel.inFormat = ((RadioButton) group.
                    getSelectedToggle()).
                    getText();
        }

        ComboBox<String> comboBox = ImportExportFrame.getFromTableComboBox();

        importExportModel.tableFrom = comboBox.getValue();
    }

    private void getUserName() {
        Session session = factory.openSession();
        String sql = "SELECT USER FROM DUAL";
        SQLQuery query = session.createSQLQuery(sql);
        java.util.List result = query.list();

        for (Object object : result) {
            username = (String) object;
        }
    }

    private Integer addExport(String tableFrom, String exportFormat, String resultOfExport, Date date, String user) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer exportID = null;
        try {
            transaction = session.beginTransaction();
            Export export = new Export();
            export.setTableFrom(tableFrom);
            export.setExpFormat(exportFormat);
            export.setExpResult(resultOfExport);
            export.setExpDate(date);
            export.setExpUser(user);
            exportID = (Integer) session.save(export);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return exportID;
    }
}

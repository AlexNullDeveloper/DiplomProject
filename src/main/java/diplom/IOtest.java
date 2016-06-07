package diplom;

import diplom.importexport.Trn;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by a.talismanov on 03.06.2016.
 */
public class IOtest {
    public static void main(String[] args){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("D:\\filename.txt"), "utf-8"));



//                   writer.append(((Integer) trn.getId()).toString());
//                   writer.append(';');
//                   writer.append(trn.getAccDeb());
//                   writer.append(';');
//
//                   writer.flush();

                writer.write("hello");
                writer.append(';');
                writer.append("poka");
                writer.append(';');

            System.out.println("vse sdelano");

        } catch (Throwable e) {
            e.getMessage();
        } finally {
            try {
                writer.close();
            } catch (Exception e){
                e.getMessage();
            }
        }

    }
}

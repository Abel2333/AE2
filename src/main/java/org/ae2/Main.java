package org.ae2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ae2.Data.*;

import java.io.IOException;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        GlobalData.initialize();
//        Stuff stuff = GlobalData.getStuff(123);
//        System.out.println(stuff);
//        GlobalData.end();
        /*if (stuff.login("testDirector")){
            GlobalData.changeStuff(stuff.getID(), stuff);
            GlobalData.end();
        }else {
            System.out.println(false);
        }*/
        Controller controller = new Controller();
        controller.Start();
    }
}
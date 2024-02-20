package de.mopla;

import de.mopla.connector.VroomRemoteService;
import de.mopla.connector.VroomRemoteServiceImpl;
import de.mopla.connector.request.Shipment;
import de.mopla.connector.request.VroomQuery;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        // 1. find and read in the file ...csv with trip requests from customers

        // 2. parse the trip requests to shipments

        final var s1 = new Shipment(List.of(1,1), );

        // 3. create vehicles

        // 4. ask vroom

        VroomRemoteService vroom = new VroomRemoteServiceImpl();

        vroom.query(new VroomQuery(List.of(s1)));

        // 5. analyze vroom output

        // create vehicles

        // 3. ask vroom



        // Press Alt+Eingabe with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Umschalt+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Umschalt+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Strg+F8.
            System.out.println("i = " + i);
        }
    }
}
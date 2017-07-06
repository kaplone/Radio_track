package fr.kaplone.libgdx;

/**
 * Created by kaplone on 09/06/17.
 */


import com.badlogic.gdx.Net.HttpMethods;

public class GoURL {

    public static void goUrl(String radio){

        String URL;

        switch (radio){
            case "divergence" : URL = "http://www.divergence-fm.org/index.php";
                                String ARGS = "page=antenne_full.php";

                                System.out.println("---> httpmanager");

                                new HttpManager(URL, HttpMethods.GET, "");
                                System.out.println("break");
                                break;

            case "fip" :        URL = "http://www.fipradio.fr/livemeta/7";

                                new HttpManager_fip(URL, HttpMethods.GET, "");
                                System.out.println("break");
                                break;
        }

        // archives fip : http://www.fipradio.fr/fip_titres_diffuses/ajax/7/1497428650/0
        //                                                                __ timesatamp --
        //
        //                                                                liste => +- 30 minutes

    }



}

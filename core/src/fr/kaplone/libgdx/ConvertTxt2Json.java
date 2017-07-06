package fr.kaplone.libgdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import jdk.nashorn.internal.ir.debug.JSONWriter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaplone on 04/07/17.
 */
public class ConvertTxt2Json {

    public static FileHandle importTxt(FileHandle file, FileHandle jsonFile) {

        List<String> ids = new ArrayList<>();
        boolean group = false;
        Resultat r = null;
        List<Resultat> resultats = new ArrayList<>();

        String[] lignes = file.readString().split("\n");

        for (String ligne : lignes) {

            if (ligne.startsWith("[")) {

                if (r != null) {
                    resultats.add(r);
                }

                r = new Resultat();
                r.setRadio(ligne.substring(1, ligne.length() - 1));

            } else if (ligne.startsWith("date")) {
                if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0) {
                    r.setDate(String.format("%s", ligne.split(" : ")[1]));
                }

            } else if (ligne.startsWith("heure")) {

                if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0) {
                    r.setHeure(String.format("%s", ligne.split(" : ")[1]));
                }

            } else if (ligne.startsWith("titre")) {

                if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0) {
                    r.setTitre(String.format("%s", ligne.split(" : ")[1]).replace("'", " "));
                }

            } else if (ligne.startsWith("interprete")) {

                if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0) {
                    r.setAuteur(String.format("%s", ligne.split(" : ")[1].replace("'", " ")));
                }

            } else if (ligne.startsWith("itunes")) {

                if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0) {
                    r.setItunes(String.format("%s", ligne.split(" : ")[1]));
                }

            } else if (ligne.startsWith("youtube")) {

                r.setYoutube(ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0 ?
                        ligne.split(" : ")[1] :
                        String.format("https://www.youtube.com/results?q=%s&sp=%s", concat(r), "CAM%253D"));

            }

        }

        Json js = new Json();
        jsonFile.writeString(js.prettyPrint(resultats), true);

        return jsonFile;
    }

    private static String concat(Resultat r){

        String[] t0 = r.getTitre().split("&");
        String[] p0 = r.getAuteur().split("&");

        List<String> liste = new ArrayList<>();

        for (String s : t0){
            liste.addAll(Arrays.asList((s.trim().split(" "))));
        }

        for (String s : p0){
            liste.addAll(Arrays.asList((s.trim().split(" "))));
        }

        String s = "";

        for (int i = 0; i < liste.size() - 1 ; i++){
            s += liste.get(i) + "+";
        }

        s += liste.get(liste.size() -1);

        return s;

    }
}


package fr.kaplone.libgdx;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;


import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kaplone on 22/06/17.
 */
public class Fip_stream implements Json.Serializable{


    private String uuid;
    private String stepId;
    private String title;
    private long start;
    private long end;
    private int fatherStepId;
    private int stationId;
    private String embedId;
    private String embedType;
    private int depth;
    private String authors;
    private String performers;
    private String titleSlug;
    private String path;
    private String lienYoutube;
    private String visuelYoutube;
    private int anneeEditionMusique;
    private String songId;
    private String visual;
    private String titreAlbum;
    private String label;
    private String releaseId;
    private String coverUuid;

    @Override
    public String toString(){

        //return String.format("%s : %s - %s\n", LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.ofHours(2)).toLocalTime().toString(), title, performers);
        return String.format("%s : %s\n            %s\n",  getTime(), title, performers);
    }

    public HorizontalGroup toBox(){
        HorizontalGroup hg = new HorizontalGroup();


        return hg;
    }

    public void write (Json json) {

    }

    public void read (Json json, JsonValue jsonMap) {

        System.out.println("_______________début read_________________");

        title = jsonMap.has("title") ? jsonMap.getString("title") : "";
        start = jsonMap.has("start") ? jsonMap.getLong("start") : 0;
        end = jsonMap.has("end") ? jsonMap.getLong("end") : 0;
        authors = jsonMap.has("authors") ? jsonMap.getString("authors") : "";
        performers = jsonMap.has("performers") ? jsonMap.getString("performers") : "";
        path = jsonMap.has("path") ? jsonMap.getString("path") : "";
        lienYoutube = jsonMap.has("lienYoutube") ? jsonMap.getString("lienYoutube") : "";
        visuelYoutube = jsonMap.has("visuelYoutube") ? jsonMap.getString("visuelYoutube") : "";
        visual = jsonMap.has("visual") ? jsonMap.getString("visual") : "";
        titreAlbum = jsonMap.has("titreAlbum") ? jsonMap.getString("titreAlbum") : "";
        anneeEditionMusique = jsonMap.has("anneeEditionMusique") ? jsonMap.getInt("anneeEditionMusique") : 0;

        System.out.println("_______________fin read_________________");
    }

    public String getTime(){

        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");

        Date date = new Date(start * 1000);
        String s = formater.format(date);

        return s;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPerformers() {
        return performers;
    }

    public String getPath() {
        return path;
    }

    public String getLienYoutube() {
        return lienYoutube;
    }

    public String getVisuelYoutube() {
        return visuelYoutube;
    }

    public int getAnneeEditionMusique() {
        return anneeEditionMusique;
    }

    public String getVisual() {
        return visual;
    }

    public String getTitreAlbum() {
        return titreAlbum;
    }

    public String getLabel() {
        return label;
    }
}

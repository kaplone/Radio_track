package fr.kaplone.libgdx.radio_track.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaplone on 23/06/17.
 */
public class Resultat {

    private String radio;
    private String date;
    private String heure;
    private String titre;
    private String auteur;
    private String itunes;
    private String youtube;
    private fr.kaplone.libgdx.radio_track.models.Fip_stream fip;
    private boolean deleted;

    public Resultat() {
    }

    public Resultat(String heure, String titre, String auteur) {
        this.heure = heure;
        this.titre = titre;
        this.auteur = auteur;
    }

    public Resultat(fr.kaplone.libgdx.radio_track.models.Fip_stream fip) {

        this.fip = fip;
        this.radio = "FIP";
    }

    public String getDate() {

        if (this.date != null){
            return this.date;
        }
        else {
            return fip.getDate();
        }

    }

    public void setDate(String date) {

        this.date = date;
    }


    public String getHeure() {
        if (this.heure != null){
            return this.heure;
        }
        else {
            return fip.getTime();
        }

    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getTitre() {
        if (this.titre != null){
            return this.titre.substring(0, Math.min(this.titre.length(), 38));
        }
        else if (this.fip != null && this.fip.getTitle() != null){
            return fip.getTitle().substring(0, Math.min(this.fip.getTitle().length(), 38));
        }
        else {
            return "";
        }
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        if (this.auteur != null){
            return this.auteur.substring(0, Math.min(this.auteur.length(), 38));
        }
        else if (this.fip != null && this.fip.getPerformers() != null){
            return fip.getPerformers().substring(0, Math.min(this.fip.getPerformers().length(), 38));
        }
        else {
            return "";
        }
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public fr.kaplone.libgdx.radio_track.models.Fip_stream getFip() {
        return fip;
    }

    public void setFip(fr.kaplone.libgdx.radio_track.models.Fip_stream fip) {
        this.fip = fip;
    }

    public boolean isYoutube() {
        return youtube != null;
    }

    public String getYoutube() {

        if (youtube != null && youtube.length() > 1){
            return youtube;
        }
        else {
            return  String.format("https://www.youtube.com/results?q=%s&sp=%s", concat(), "CAM%253D");
        }
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getItunes() {
        return itunes;
    }

    public void setItunes(String itunes) {
        this.itunes = itunes;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getId(){

        return this.getRadio() + this.getDate() + this.getTitre() + this.getAuteur();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private String concat(){

        String[] t0 = this.getTitre().split("&");
        String[] p0 = this.getAuteur().split("&");

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

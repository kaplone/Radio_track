package fr.kaplone.libgdx;

/**
 * Created by kaplone on 23/06/17.
 */
public class Resultat {

    private String date;
    private String heure;
    private String titre;
    private String auteur;
    private Fip_stream fip;
    private boolean youtube;

    public Resultat() {
    }

    public Resultat(String heure, String titre, String auteur) {
        this.heure = heure;
        this.titre = titre;
        this.auteur = auteur;
    }

    public Resultat(Fip_stream fip) {
        this.fip = fip;
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

    public Fip_stream getFip() {
        return fip;
    }

    public void setFip(Fip_stream fip) {
        this.fip = fip;
    }

    public boolean isYoutube() {
        return youtube;
    }

    public void setYoutube(boolean youtube) {
        this.youtube = youtube;
    }
}

package fr.kaplone.libgdx.radio_track.models;

/**
 * Created by kaplone on 29/07/17.
 */
public class Cases {

    private boolean checked;

    private final String NUE = "case.png";
    private final String COCHE = "case_coche.png";

    public Cases(boolean c){
        this.checked = c;
    }

    public String getpath(){
        return checked ? COCHE : NUE;
    }

    public void touch(){
        this.checked = ! this.checked;
    }

    public boolean isChecked(){
        return this.checked;
    }

    public void disable(){
        this.checked = false;
    }

}

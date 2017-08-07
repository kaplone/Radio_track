package fr.kaplone.libgdx.radio_track.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by kaplone on 07/08/17.
 */
public class FontFactory {

    public static BitmapFont getFont(String path, int size, Color couleur){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = 0;
        parameter.color = couleur;
        parameter.shadowOffsetX = 0;
        parameter.shadowOffsetY = 0;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        return font24;

        //Label.LabelStyle labelStyle = new Label.LabelStyle();
        //labelStyle.font = font24;
    }

    public static BitmapFont getBlackFont(String path, int size) {
        return getFont(path, size, Color.BLACK);
    }

    public static BitmapFont getWhiteFont(String path, int size) {
        return getFont(path, size, Color.WHITE);
    }

    public static BitmapFont getStdFont(int size) {
        return getWhiteFont("fonts/Aller_Std_Rg.ttf", size);
    }
}

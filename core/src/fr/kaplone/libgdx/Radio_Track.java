package fr.kaplone.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.*;
import java.util.List;


public class Radio_Track extends ApplicationAdapter {

	Stage stage;
	SpriteBatch batch;
	BitmapFont font;
	Texture divergence;
	Texture fip;
	Texture playList;
	Texture radio;
	Texture divergence_ico;
	Texture fip_ico;
	Texture barre;
	Texture trash;
	Texture play;
	Texture youtube;

	float fip_w;
	float fip_h;
	float fip_x;
	float fip_y;

	float divergence_w;
	float divergence_h;
	float divergence_x;
	float divergence_y;
	
	float playList_w;
	float playList_h;
	float playList_x;
	float playList_y;

	static List<Resultat> resultats;

	private Skin skin;
	private Skin default_;
	private Table table;
	private Table table_playList;
	private ScrollPane scroll;
	private ScrollPane scroll_playList;

	private String filename;
	private FileHandle file;

	private int page;
	
	@Override
	public void create () {

		skin = new Skin(Gdx.files.internal("lgdxsui/lgdxs-ui.json"));
		default_ = new Skin(Gdx.files.internal("default/uiskin.json"));
		batch = new SpriteBatch();
		divergence = new Texture("divergence.jpg");
		fip = new Texture("fip.jpg");
		playList = new Texture("playlist-512.png");
		radio = new Texture("radio.png");
		divergence_ico = new Texture("divergence_.jpg");
		fip_ico = new Texture("fip.png");
		barre = new Texture("barre_orange.png");
		trash = new Texture("trash_grise.png");
		play = new Texture("play.png");
		youtube = new Texture("YouTube.png");

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		page = 1;

		font = new BitmapFont();

		divergence_w = 540;
		divergence_h = 300;
		divergence_x = 75;
		divergence_y = 850;

		fip_w = 540;
		fip_h = 300;
		fip_x = 75;
		fip_y = 450;
		
		playList_w = 128;
		playList_h = 100;
		playList_x = 20;
		playList_y = 70;

		final Actor divergence_actor = new Actor();
		final Actor fip_actor = new Actor();
		final Actor playList_actor = new Actor();
		final Actor radio_actor = new Actor();

		playList_actor.setSize(playList_w,playList_h);
		playList_actor.setPosition(playList_x, playList_y);
		playList_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				scroll.setVisible(false);
				scroll_playList.setVisible(true);
				table_playList.clear();

				String dateEnCours;
				String dateEnCache = "";
				TextButton t = null;

				String[] lignes = new String[0];

				if (file.exists()){
					lignes = file.readString().split("\n");
				}

				VerticalGroup vg = null;
				HorizontalGroup hg = null;
				boolean group = false;

				TextButton tbh;
				TextButton tbt;
				TextButton tbp;

				List<String> ids = new ArrayList<>();
				List<VerticalGroup> vgs = new ArrayList<>();
				String id = "";

				if (! stage.getActors().contains(scroll_playList, true)){
					System.out.println("ajout du scroll au stage ...");
					stage.addActor(scroll_playList);
				}

				int nb_icons = 0;

				for (String ligne : lignes){

					if (ligne.startsWith("[")) {

						System.out.println("radio " + id);

						if (group) {

							if (! ids.contains(id)){
								ids.add(id);
								vg.addActorAt(0, hg);
								vgs.add(vg);

								Image im = new Image(barre);
								VerticalGroup h_barre = new VerticalGroup();
								h_barre.addActor(im);
								vgs.add(h_barre);

								System.out.println("ajout regulier : VG");

								nb_icons = 0;
							}
						}

						id = ligne.substring(1, ligne.length() - 1);

						vg = new VerticalGroup();
//						System.out.println("red = " + vg.getColor().r);
//						System.out.println("green = " + vg.getColor().g);
//						System.out.println("blue = " + vg.getColor().b);
//						System.out.println("alpha = " + vg.getColor().a);
						vg.draw(batch, 1.0f);

						hg = new HorizontalGroup();

						group = true;

						Image i = new Image( id.equals("FIP") ? fip_ico : divergence_ico);
						i.setAlign(Align.left);
						hg.addActor(i);

						nb_icons ++;

						Image im = new Image(trash);
						hg.addActor(im);

						nb_icons ++;

						hg.space((500 - (i.getWidth() + im.getWidth())) / nb_icons);
					}

					else if (ligne.startsWith("date")) {

						dateEnCours = ligne.split(" : ")[1];
						System.out.println(dateEnCours);

						if (! vgs.isEmpty() && t != null && ! ids.contains(dateEnCours) && ! dateEnCours.equals(dateEnCache)){
							ids.add(dateEnCache);

							VerticalGroup vg_date = new VerticalGroup();
							t.setTransform(true);
							t.scaleBy(0.7f);
							t.setOrigin(Align.center);
							vg_date.addActor(t);
							vgs.add(vg_date);
							System.out.println("ajout regulier : " + dateEnCache);

							Image im = new Image(barre);
							VerticalGroup h_barre = new VerticalGroup();
							h_barre.addActor(im);
							vgs.add(h_barre);

						}

						t = new TextButton(dateEnCache , skin, "oval4");
						t.setName(dateEnCache);
						t.setTouchable(Touchable.disabled);

						dateEnCache = dateEnCours;

					}

					else if (ligne.startsWith("heure")){

						if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0){
							id += "-" + ligne.split(" : ")[1];

							tbh = new TextButton(ligne.split(" : ")[1], skin, "oval4");
							tbh.setTouchable(Touchable.disabled);
							hg.addActor(tbh);

						}
					}

					else if (ligne.startsWith("titre")){

						if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0){
							id += "-" + ligne.split(" : ")[1];

							tbt = new TextButton(normaliser(ligne.split(" : ")[1]), skin, "oval3");
							tbt.setTouchable(Touchable.disabled);
							vg.addActor(tbt);
						}
					}
					else if (ligne.startsWith("interprete")){

						if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0){
							id += "-" + ligne.split(" : ")[1];

							tbp = new TextButton(normaliser(ligne.split(" : ")[1]), skin, "oval5");
							tbp.setTouchable(Touchable.disabled);
							vg.addActor(tbp);
						}
					}
					else if (ligne.startsWith("itunes")){

						if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0){

							final String URL = ligne.split(" : ")[1];

							Image im = new Image(play);
							im.setTouchable(Touchable.enabled);

							nb_icons ++;

							hg.space(nb_icons <= 3 ? 70 : 40);
							hg.addActorAt(1, im);

							im.addListener(new InputListener() {
								@Override
								public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

									Gdx.net.openURI(URL);

									return  true;
								}
							});
						}
					}
					else if (ligne.startsWith("youtube")){

						if (ligne.split(" : ").length > 1 && ligne.split(" : ")[1].trim().length() > 0){

							final String URL = ligne.split(" : ")[1];

							Image im = new Image(youtube);
							im.setTouchable(Touchable.enabled);

							nb_icons ++;

							hg.space(nb_icons <= 3 ? 70 : 40);

							hg.addActorAt(1, im);

							im.addListener(new InputListener() {
								@Override
								public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

									Gdx.net.openURI(URL);

									return  true;
								}
							});
						}
					}

				}

				if (! ids.contains(id)){
					vg.addActorAt(0, hg);
					vgs.add(vg);

					System.out.println("ajout final : VG");

				}

				if (t != null && ! ids.contains(t.getName())){
					VerticalGroup vg_date = new VerticalGroup();

					t.setTransform(true);
					t.scaleBy(0.7f);
					t.setOrigin(Align.center);

					vg_date.addActor(t);

					vgs.add(vg_date);

					System.out.println("ajout final : " + t.getName());

					Image im = new Image(barre);
					VerticalGroup h_barre = new VerticalGroup();
					h_barre.addActor(im);
					vgs.add(h_barre);
				}

				for (int i = vgs.size() -1; i >= 0; i--){
					table_playList.add(vgs.get(i)).padTop(50).row();
				}

				System.out.println(vgs.size());

				scroll_playList.setScrollY(0);

				page = 2;

				radio_actor.setTouchable(Touchable.enabled);
				playList_actor.setTouchable(Touchable.disabled);
				divergence_actor.setTouchable(Touchable.disabled);
				fip_actor.setTouchable(Touchable.disabled);

				return true;
			}
		});

		radio_actor.setSize(playList_w,playList_h);
		radio_actor.setPosition(playList_x, playList_y);
		radio_actor.setTouchable(Touchable.disabled);
		radio_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				scroll.setVisible(true);
				scroll_playList.setVisible(false);

				page = 1;

				radio_actor.setTouchable(Touchable.disabled);
				playList_actor.setTouchable(Touchable.enabled);
				divergence_actor.setTouchable(Touchable.enabled);
				fip_actor.setTouchable(Touchable.enabled);


				return true;
			}
		});

		stage.addActor(radio_actor);
		stage.addActor(playList_actor);

		divergence_actor.setSize(divergence_w,divergence_h);
		divergence_actor.setPosition(divergence_x,divergence_y);
		divergence_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				divergence_w = 650;
				divergence_h = 375;
				divergence_x = 30;
				divergence_y = 850;

				fip_w = 360;
				fip_h = 200;
				fip_x = 175;
				fip_y = 50;

				fip_actor.setSize(fip_w,fip_h);
				fip_actor.setPosition(fip_x,fip_y);

				divergence_actor.setSize(divergence_w,divergence_h);
				divergence_actor.setPosition(divergence_x,divergence_y);

				resultats.clear();

				if ( ! stage.getActors().contains(scroll, true)){
					stage.addActor(scroll);
				}


				GoURL.goUrl("divergence");

				while (resultats.size() == 0){
                    table.clear();
					try {
						Thread.sleep(200);
					}
					catch (InterruptedException ie){
						ie.printStackTrace();
					}
				}

				TextButton tbh;
				TextButton tbt;
				TextButton tbp;

				final Map<String, Resultat> resultatMap = new HashMap<>();


				for (Resultat r : resultats){

					String key = String.format("%s_%s_not_saved", r.getHeure(), r.getTitre());
					resultatMap.put(key, r);

					final VerticalGroup vg = new VerticalGroup();
					final HorizontalGroup hg = new HorizontalGroup();
					vg.padTop(30);
					//vg.setTouchable(Touchable.disabled);

					tbh = new TextButton(r.getHeure(), skin, "oval4");
					tbh.setTouchable(Touchable.disabled);
					tbh.setName(r.getHeure());
					hg.addActor(tbh);

					final TextButton tbb = new TextButton("Not Saved", skin, "oval2");
					tbb.setTouchable(Touchable.enabled);
					tbb.setName(key);
					hg.space(400);
					hg.addActor(tbb);

					tbt = new TextButton(normaliser(r.getTitre()), skin, "oval3");
					tbt.setTouchable(Touchable.disabled);
					tbt.setName(r.getTitre());
					tbt.setWidth(Gdx.graphics.getWidth() - 50);

					tbp = new TextButton(normaliser(r.getAuteur()), skin, "oval5");
					tbp.setTouchable(Touchable.disabled);
					tbp.setName(r.getAuteur());
					tbp.setWidth(Gdx.graphics.getWidth() - 50);

					vg.addActor(hg);
					vg.addActor(tbt);
					vg.addActor(tbp);

					tbb.addListener(new InputListener(){
						@Override
						public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

							String nom = tbb.getName();
							HorizontalGroup hg_ = (HorizontalGroup) tbb.getParent();

							tbb.remove();

							TextButton tbb_ = null;

							if (nom.contains("not_saved")){
								tbb_ = new TextButton("Saved", skin, "oval0");

								Resultat r_ = resultatMap.get(nom);


								file.writeString("[DIVERGENCE FM]\n", true);
								file.writeString("date : " + r_.getDate() + "\n", true);
								file.writeString("heure : " + r_.getHeure() + "\n", true);
								file.writeString("titre : " + r_.getTitre() + "\n", true);
								file.writeString("interprete : " + r_.getAuteur() + "\n", true);
								file.writeString("\n", true);

								tbb_.setName(nom.replace("not_saved", ""));
							}

							tbb_.setTouchable(Touchable.disabled);
							hg_.space(400);
							hg_.addActor(tbb_);


							return true;
						}
					});

					table.add(vg);
					table.row();
				}

				scroll.setScrollY(0);
				return true;
			}
		});
		divergence_actor.setTouchable(Touchable.enabled);
		stage.addActor(divergence_actor);

		fip_actor.setSize(fip_w,fip_h);
		fip_actor.setPosition(fip_x,fip_y);
		fip_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				divergence_w = 360;
				divergence_h = 200;
				divergence_x = 175;
				divergence_y = 50;

				fip_w = 650;
				fip_h = 375;
				fip_x = 30;
				fip_y = 850;

				divergence_actor.setSize(divergence_w,divergence_h);
				divergence_actor.setPosition(divergence_x,divergence_y);

				fip_actor.setSize(fip_w,fip_h);
				fip_actor.setPosition(fip_x,fip_y);

				resultats.clear();

                if ( ! stage.getActors().contains(scroll, true)){
                	stage.addActor(scroll);
				}


				GoURL.goUrl("fip");

				while (resultats.size() == 0){
                    table.clear();
					try {
						Thread.sleep(200);

					}
					catch (InterruptedException ie){
						ie.printStackTrace();
					}
				}

				TextButton tbh;
				TextButton tbt;
				TextButton tbp;

				final Map<String, Resultat> resultatMap = new HashMap<>();


				for (Resultat r : resultats){

					String key = String.format("%s_%s_not_saved", r.getHeure(), r.getTitre());
					resultatMap.put(key, r);

					final VerticalGroup vg = new VerticalGroup();
					final HorizontalGroup hg = new HorizontalGroup();
					vg.padTop(30);
					//vg.setTouchable(Touchable.disabled);

					tbh = new TextButton(r.getHeure(), skin, "oval4");
					tbh.setTouchable(Touchable.disabled);
					tbh.setName(r.getHeure());
					hg.addActor(tbh);

					final TextButton tbb = new TextButton("Not Saved", skin, "oval2");
					tbb.setTouchable(Touchable.enabled);
					tbb.setName(key);
					hg.space(400);
					hg.addActor(tbb);

					tbt = new TextButton(normaliser(r.getTitre()), skin, "oval3");
					tbt.setTouchable(Touchable.disabled);
					tbt.setName(r.getTitre());
					tbt.setWidth(Gdx.graphics.getWidth() - 50);

					tbp = new TextButton(normaliser(r.getAuteur()), skin, "oval5");
					tbp.setTouchable(Touchable.disabled);
					tbp.setName(r.getAuteur());
					tbp.setWidth(Gdx.graphics.getWidth() - 50);

					vg.addActor(hg);
					vg.addActor(tbt);
					vg.addActor(tbp);

					tbb.addListener(new InputListener(){
						@Override
						public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

							String nom = tbb.getName();
							HorizontalGroup hg_ = (HorizontalGroup) tbb.getParent();

							tbb.remove();

							TextButton tbb_ = null;

							if (nom.contains("not_saved")){
								tbb_ = new TextButton("Saved", skin, "oval0");

								Resultat r_ = resultatMap.get(nom);


								file.writeString("[FIP]\n", true);
								file.writeString("date : " + r_.getDate() + "\n", true);
								file.writeString("heure : " + r_.getHeure() + "\n", true);
								file.writeString("titre : " + r_.getTitre() + "\n", true);
								file.writeString("interprete : " + r_.getAuteur() + "\n", true);
								file.writeString("itunes : " + r_.getFip().getPath() + "\n", true);
								file.writeString("youtube : " + r_.getFip().getLienYoutube() + "\n", true);
								file.writeString("\n", true);

								tbb_.setName(nom.replace("not_saved", ""));
							}

							tbb_.setTouchable(Touchable.disabled);
							hg_.space(400);
							hg_.addActor(tbb_);


							return true;
						}
					});

					table.add(vg);
					table.row();
				}

				scroll.setScrollY(0);
				return true;
			}
		});
		fip_actor.setTouchable(Touchable.enabled);
		stage.addActor(fip_actor);

		resultats = new ArrayList<>();

		table = new Table();
        table.align(Align.center);
        table.setWidth(Gdx.graphics.getWidth() - 30);

		table_playList = new Table();
        table_playList.align(Align.center);
        table_playList.setWidth(Gdx.graphics.getWidth() - 30);


        scroll = new ScrollPane(table);
        scroll.setWidth(Gdx.graphics.getWidth() - 20);
        scroll.setHeight(500);
        scroll.setPosition(0, 300);
		//table.setDebug(true);

        scroll_playList = new ScrollPane(table_playList);
        scroll_playList.setWidth(Gdx.graphics.getWidth() - 20);
        scroll_playList.setHeight(1000);
        scroll_playList.setPosition(10, 200);
		//table_playList.setDebug(true);

		filename = "radio_track.txt";
		file = Gdx.files.external(filename);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		batch.begin();

		switch (page){
			case 1 : batch.draw(divergence, divergence_x, divergence_y, divergence_w, divergence_h);
				     batch.draw(fip, fip_x, fip_y, fip_w, fip_h);
					 batch.draw(playList, playList_x, playList_y, playList_w, playList_h);
					 break;
			case 2 : batch.draw(radio, playList_x, playList_y, playList_w, playList_h);
			         break;
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		divergence.dispose();
		fip.dispose();
	}

	public static void setResultats(List<Resultat> resultats_) {
		resultats = resultats_;
		System.out.println("setResultats() : " + resultats.size());
	}

	private String normaliser(String s){
		return s.substring(0, Math.min(30, s.length())).replace("é", "e")
				                                          .replace("è", "e")
														  .replace("ê", "e")
														  .replace("ë", "e")
														  .replace("à", "a")
														  .replace("û", "u")
														  .replace("ô", "o")
														  .replace("î", "i")
														  .replace("À", "A")
														  .replace("Û", "U")
														  .replace("Ô", "O")
														  .replace("Î", "I")
														  .replace("É", "E")
														  .replace("È", "E")
														  .replace("Ê", "E")
				+ (s.length() > 30 ? "..." : "");
	}
}

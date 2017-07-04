package fr.kaplone.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private String jsonFilename;
	private FileHandle file;
	private FileHandle jsonFile;

	private SimpleDateFormat formater;

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

		formater = new SimpleDateFormat("dd/MM/yyyy");

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

				/*
				 * Le première version diffusée enregistrait les informatione dans un format TXT.
				 * Son parsage était laborieux (pas de tag de sortie de bloc)
				 *
				 * Si des enregistrements dans ce format existent, ils sont convertis au format Json, puis le fichier .txt est supprimé.
				 */

				if (! jsonFile.exists()){

					if (file.exists()){
						ConvertTxt2Json.importTxt(file, jsonFile);
					}

					else {
						jsonFile.writeString("", true);
					}
				}

				VerticalGroup vg = new VerticalGroup();
				VerticalGroup vg1 = new VerticalGroup();
				HorizontalGroup hg = new HorizontalGroup();
				HorizontalGroup hg1 = new HorizontalGroup();
				HorizontalGroup hg2 = new HorizontalGroup();
				boolean group = false;

				TextButton tbh;
				TextButton tbt;
				TextButton tbp;

				List<String> ids = new ArrayList<>();
				List<VerticalGroup> vgs = new ArrayList<>();
				String id = "";
				String radio = "";

				String date_courante = "";

				if (! stage.getActors().contains(scroll_playList, true)){
					System.out.println("ajout du scroll au stage ...");
					stage.addActor(scroll_playList);
				}

                final Json js = new Json();
				resultats = js.fromJson(ArrayList.class, Resultat.class, jsonFile);

				Comparator<Resultat> resultatComparator = new Comparator<Resultat>() {
					@Override
					public int compare(Resultat resultat, Resultat t1) {
						try {
							return formater.parse(t1.getDate()).compareTo(formater.parse(resultat.getDate())) != 0 ?
									formater.parse(t1.getDate()).compareTo(formater.parse(resultat.getDate())) :
									t1.getHeure().compareTo(resultat.getHeure());
						}
						catch (ParseException pe){
							return 0;
						}

					}
				};

				Collections.sort(resultats, resultatComparator);

				for (Resultat r : resultats){

					if (! r.isDeleted() && ! ids.contains(r.getId())){

						ids.add(r.getId());

						vg = new VerticalGroup();
						vg1 = new VerticalGroup();
						hg = new HorizontalGroup();
						hg1 = new HorizontalGroup();
						hg2 = new HorizontalGroup();
						vg.draw(batch, 1.0f);
						vg.space(10);
						vg.expand(true);


						if (! date_courante.equals(r.getDate())){


							Image im_barre = new Image(barre);
							VerticalGroup h_barre_ = new VerticalGroup();
							h_barre_.addActor(im_barre);
							vgs.add(h_barre_);

							t = new TextButton(r.getDate() , skin, "oval4");
							t.setName(r.getDate());
							t.setTouchable(Touchable.disabled);

							VerticalGroup vg_date = new VerticalGroup();
							t.setTransform(true);
							t.scaleBy(0.7f);
							t.setOrigin(Align.center);
							vg_date.addActor(t);
							vgs.add(vg_date);


							date_courante = r.getDate();
						}


						Image im = new Image(barre);
						VerticalGroup h_barre = new VerticalGroup();
						h_barre.addActor(im);
						vgs.add(h_barre);

						Image i = new Image( r.getRadio().equals("FIP") ? fip_ico : divergence_ico);
						i.setAlign(Align.left);
						hg.addActor(i);
						hg.align(Align.left);

						hg.space(20);

//						switch (radio){
//							case "FIP" : hg.space(nb_icons <= 3 ? 70 : 40);
//								break;
//							case "DIVERGENCE FM" : hg.space(nb_icons <= 3 ? 58 : 30);
//								break;
//						}


						tbh = new TextButton(r.getHeure(), skin, "oval4");
						tbh.setTouchable(Touchable.disabled);
						hg.addActor(tbh);

						hg.space(60);


						Image im_yt = new Image(youtube);
						im_yt.setTouchable(Touchable.enabled);
						hg.addActor(im_yt);

						final Resultat rf = r;
						im_yt.addListener(new InputListener() {
							@Override
							public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
								Gdx.net.openURI(rf.getYoutube());
								return  true;
							}
						});


						if (r.getItunes() != null && r.getItunes().length() > 1){

							Image im_it = new Image(play);
							im_it.setTouchable(Touchable.enabled);
							hg.addActor(im_it);

							im_it.addListener(new InputListener() {
								@Override
								public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
									Gdx.net.openURI(rf.getItunes());
									return  true;
								}
							});
						}

						vg.addActor(hg);

						tbt = new TextButton(normaliser(r.getTitre()), skin, "oval3");
						tbt.setTouchable(Touchable.disabled);
						vg1.addActor(tbt);

						tbp = new TextButton(normaliser(r.getAuteur()), skin, "oval5");
						tbp.setTouchable(Touchable.disabled);
						vg1.addActor(tbp);

						hg1.addActor(vg1);
						vg.addActor(hg1);

						Image im_tr = new Image(trash);
						im_tr.setTouchable(Touchable.enabled);
						im_tr.setAlign(Align.left);
						hg2.align(Align.left);
						hg2.fill();
						hg2.expand(true);
						hg2.setWidth(300);
						hg2.addActor(im_tr);

						im_tr.addListener(new InputListener() {
							@Override
							public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
								rf.setDeleted(true);
								jsonFile = Gdx.files.external(jsonFilename);
								jsonFile.writeString(js.toJson(resultats), false);

								InputEvent event1 = new InputEvent();
								event1.setType(InputEvent.Type.touchDown);
								playList_actor.fire(event1);

								return  true;
							}
						});
                        vg.align(Align.left);
						vg.addActor(hg2);

						vgs.add(vg);

					}

				}


				for (int i = 0; i < vgs.size(); i++){
					table_playList.add(vgs.get(i)).padTop(50).row();
				}

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
		jsonFilename = "radio_track.json";
		file = Gdx.files.external(filename);
		jsonFile = Gdx.files.external(jsonFilename);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
		//stage.setDebugAll(true);

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

	private String concat(Resultat r){

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

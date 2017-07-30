package fr.kaplone.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SnapshotArray;

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

	Texture menu_carres;
	Texture menu_radio;
	Texture menu_recherche;
	Texture menu_playlist;
	Texture menu_bandeau;
	Texture menu_options;
	Texture fond_options;
	Texture fond_options_haut;
	Texture no_trash;
	Texture menu_play;
	Texture menu_yt;
	Texture menu_horloge;
	Texture menu_calendrier;

	Texture case_nue;
	Texture case_coche;
	Texture case_1;
	Texture case_2;
	Texture case_3;
	Texture case_4;
	Texture case_5;
	Texture case_6;
	Texture case_7;
	Texture case_8;

	Cases c1;
	Cases c2;
	Cases c3;
	Cases c4;
	Cases c5;
	Cases c6;
	Cases c7;
	Cases c8;

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

	float menu_bandeau_w;
	float menu_bandeau_h;
	float menu_bandeau_y;

	float menu_icons_h;
	float menu_icons_w1;
	float menu_icons_x1;
	float menu_icons_w2;
	float menu_icons_x2;
	float menu_icons_w3;
	float menu_icons_x3;
	float menu_icons_y;

	String motif_recherche;


	static List<Resultat> resultats;
	List<Resultat> resultatsPlayList;

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

	private boolean options;
	private boolean recherche;
	
	@Override
	public void create () {

		stage = new Stage();

		final Actor divergence_actor = new Actor();
		final Actor fip_actor = new Actor();
		final Actor playList_actor = new Actor();
		final Actor radio_actor = new Actor();

		final Actor menu_actor = new Actor();
		final Actor menu_recherche_actor = new Actor();

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

		menu_carres = new Texture("carres_menu.png");
		menu_radio = new Texture("rt_bico.png");
		menu_recherche = new Texture("recherche.png");
		menu_playlist = new Texture("playlist_bico.png");
		menu_bandeau = new Texture("bandeau_menu.png");
		menu_options = new Texture("bandeau_menu.png");
		fond_options = new Texture("fond_options.png");
		fond_options_haut = new Texture("fond_options_haut.png");
		no_trash = new Texture("no-trash.png");
		menu_play = new Texture("play.png");
		menu_yt = new Texture("YouTube.png");
		menu_horloge = new Texture("horloge.png");
		menu_calendrier = new Texture("calendrier.png");

		case_nue = new Texture("case.png");
		case_coche = new Texture("case_coche.png");

		c1 =new Cases(true);
		c2 =new Cases(true);
		c3 =new Cases(false);
		c4 =new Cases(false);
		c5 =new Cases(true);
		c6 =new Cases(true);
		c7 =new Cases(true);
		c8 =new Cases(true);

		case_1 = new Texture(c1.getpath());
		case_2 = new Texture(c2.getpath());
		case_3 = new Texture(c3.getpath());
		case_4 = new Texture(c4.getpath());
		case_5 = new Texture(c5.getpath());
		case_6 = new Texture(c6.getpath());
		case_7 = new Texture(c7.getpath());
		case_8 = new Texture(c8.getpath());

		final Actor case_1_actor = new Actor();
		case_1_actor.setSize(80, 80);
		case_1_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 100);
		case_1_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c1.touch();
				case_1 = new Texture(c1.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_1_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_1_actor);

		final Actor case_2_actor = new Actor();
		case_2_actor.setSize(80, 80);
		case_2_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 230);
		case_2_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c2.touch();
				case_2 = new Texture(c2.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_2_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_2_actor);

		final Actor case_3_actor = new Actor();
		case_3_actor.setSize(80, 80);
		case_3_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 360);
		case_3_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c3.touch();
				case_3 = new Texture(c3.getpath());
				c4.disable();
				case_4 = new Texture(c4.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_3_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_3_actor);

		final Actor case_4_actor = new Actor();
		case_4_actor.setSize(80, 80);
		case_4_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 490);
		case_4_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c4.touch();
				case_4 = new Texture(c4.getpath());
				c3.disable();
				case_3 = new Texture(c3.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_4_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_4_actor);

		final Actor case_5_actor = new Actor();
		case_5_actor.setSize(80, 80);
		case_5_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 620);
		case_5_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c5.touch();
				case_5 = new Texture(c5.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_5_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_5_actor);

		final Actor case_6_actor = new Actor();
		case_6_actor.setSize(80, 80);
		case_6_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 750);
		case_6_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c6.touch();
				case_6 = new Texture(c6.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_6_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_6_actor);

		final Actor case_7_actor = new Actor();
		case_7_actor.setSize(80, 80);
		case_7_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 880);
		case_7_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c7.touch();
				case_7 = new Texture(c7.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_7_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_7_actor);

		final Actor case_8_actor = new Actor();
		case_8_actor.setSize(80, 80);
		case_8_actor.setPosition(585, Gdx.graphics.getHeight() - 120 - 1010);
		case_8_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				c8.touch();
				case_8 = new Texture(c8.getpath());

				InputEvent event1 = new InputEvent();
				event1.setType(InputEvent.Type.touchDown);
				playList_actor.fire(event1);

				return true;
			}
		});

		case_8_actor.setTouchable(Touchable.disabled);
		stage.addActor(case_8_actor);

		Gdx.input.setInputProcessor(stage);

		formater = new SimpleDateFormat("dd/MM/yyyy");

		motif_recherche = "";

		page = 1;

		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		divergence_w = 540;
		divergence_h = 300;
		divergence_x = 75;
		divergence_y = 700;

		fip_w = 540;
		fip_h = 300;
		fip_x = 75;
		fip_y = 250;
		
		playList_w = 128;
		playList_h = 100;
		playList_x = 20;
		playList_y = 270;

		menu_bandeau_h = 96;
		menu_bandeau_y = Gdx.graphics.getHeight() - menu_bandeau_h - 2;
		menu_bandeau_w = Gdx.graphics.getWidth() - 4;

		menu_icons_h = 72;
		menu_icons_y = Gdx.graphics.getHeight() - menu_icons_h - 12;
		menu_icons_x1 = 20;
		menu_icons_w1 = 72;
		menu_icons_x2 = 72 + menu_icons_x1 + 20;
		menu_icons_w2 = Gdx.graphics.getWidth() - (72 + menu_icons_x1 + 20) * 2;
		menu_icons_x3 = Gdx.graphics.getWidth() - 24 - menu_icons_x1;
		menu_icons_w3 = 24;

		menu_recherche_actor.setSize(menu_icons_w2, menu_icons_h);
		menu_recherche_actor.setPosition(menu_icons_x2, menu_icons_y);
		menu_recherche_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.input.setOnscreenKeyboardVisible(true);

				Gdx.input.getTextInput(new Input.TextInputListener() {
					@Override
					public void input(String text) {
						motif_recherche = text;
						InputEvent event1 = new InputEvent();
						event1.setType(InputEvent.Type.touchDown);
						playList_actor.fire(event1);
					}

					@Override
					public void canceled() {
						motif_recherche = "";
						InputEvent event1 = new InputEvent();
						event1.setType(InputEvent.Type.touchDown);
						playList_actor.fire(event1);

					}
				}, "Recherche dans les titres ou dans les interprètes :", motif_recherche, "Saisie ...");


				return true;
			}
		});

		stage.addActor(menu_recherche_actor);
		menu_recherche_actor.setTouchable(Touchable.disabled);

		menu_actor.setSize(menu_icons_w3, menu_icons_h);
		menu_actor.setPosition(menu_icons_x3, menu_icons_y);
		menu_actor.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				options = !options;
				scroll_playList.setTouchable(options ? Touchable.disabled : Touchable.enabled);
				case_1_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_2_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_3_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_4_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_5_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_6_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_7_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				case_8_actor.setTouchable(options ? Touchable.enabled : Touchable.disabled);
				return true;
			}
		});

		stage.addActor(menu_actor);
		menu_actor.setTouchable(Touchable.enabled);

		playList_actor.setSize(menu_icons_w1, menu_icons_h);
		playList_actor.setPosition(menu_icons_x1, menu_icons_y);

		//playList_actor.setSize(playList_w,playList_h);
		//playList_actor.setPosition(playList_x, playList_y);
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
				resultatsPlayList = js.fromJson(ArrayList.class, Resultat.class, jsonFile);

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

				if (resultatsPlayList == null){
					resultatsPlayList = new ArrayList<>();
				}

				Collections.sort(resultatsPlayList, resultatComparator);

				for (Resultat r : resultatsPlayList){

					if ( r.getRadio().equals("FIP") && c2.isChecked() || r.getRadio().equals("DIVERGENCE FM") && c1.isChecked()) {
						if (motif_recherche.equals("")
								|| r.getAuteur().toLowerCase().contains(motif_recherche.toLowerCase())
								|| r.getTitre().toLowerCase().contains(motif_recherche.toLowerCase())){

							if (! ids.contains(r.getId()) && (r.isDeleted() && c4.isChecked() || ! r.isDeleted() && ! c4.isChecked())){

								ids.add(r.getId());

								vg = new VerticalGroup();
								vg1 = new VerticalGroup();
								hg = new HorizontalGroup();
								hg1 = new HorizontalGroup();
								hg2 = new HorizontalGroup();
								vg.draw(batch, 1.0f);
								vg.space(10);
								vg.expand(true);


								if (! date_courante.equals(r.getDate()) && c8.isChecked()){


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

                                if (c7.isChecked()){
									tbh = new TextButton(r.getHeure(), skin, "oval4");
									tbh.setTouchable(Touchable.disabled);
									hg.addActor(tbh);

									hg.space(60);
								}

								final Resultat rf = r;

                                if(c6.isChecked()){
									Image im_yt = new Image(youtube);
									im_yt.setTouchable(Touchable.enabled);
									hg.addActor(im_yt);
									im_yt.addListener(new InputListener() {
										@Override
										public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
											Gdx.net.openURI(rf.getYoutube());
											return  true;
										}
									});
								}

								if (c5.isChecked() && r.getItunes() != null && r.getItunes().length() > 1){

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

								if (c3.isChecked()){
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
											jsonFile.writeString(js.prettyPrint(resultatsPlayList), false);

											InputEvent event1 = new InputEvent();
											event1.setType(InputEvent.Type.touchDown);
											playList_actor.fire(event1);

											return  true;
										}
									});
									vg.align(Align.left);
									vg.addActor(hg2);
								}

								if (c4.isChecked()){
									Image im_tr = new Image(no_trash);
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
											rf.setDeleted(false);
											jsonFile = Gdx.files.external(jsonFilename);
											jsonFile.writeString(js.prettyPrint(resultatsPlayList), false);

											InputEvent event1 = new InputEvent();
											event1.setType(InputEvent.Type.touchDown);
											playList_actor.fire(event1);

											return  true;
										}
									});
									vg.align(Align.left);
									vg.addActor(hg2);
								}

								vgs.add(vg);

							}
						}
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
				menu_recherche_actor.setTouchable(Touchable.enabled);

				return true;
			}
		});


		radio_actor.setSize(menu_icons_w1, menu_icons_h);
		radio_actor.setPosition(menu_icons_x1, menu_icons_y);

//		radio_actor.setSize(playList_w,playList_h);
//		radio_actor.setPosition(playList_x, playList_y);
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

				menu_recherche_actor.setTouchable(Touchable.disabled);


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
				divergence_y = 800;

				fip_w = 360;
				fip_h = 200;
				fip_x = 175;
				fip_y = 100;

				fip_actor.setSize(fip_w,fip_h);
				fip_actor.setPosition(fip_x,fip_y);

				divergence_actor.setSize(divergence_w,divergence_h);
				divergence_actor.setPosition(divergence_x,divergence_y);

				resultats.clear();

				if ( ! stage.getActors().contains(scroll, true)){
					stage.addActor(scroll);
				}

				System.out.println("---> divergence");


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

								final Json js = new Json();
								resultats = js.fromJson(ArrayList.class, Resultat.class, jsonFile);

								if (resultats == null){
									resultats = new ArrayList<>();
								}

								resultats.add(resultatMap.get(nom));

								jsonFile.writeString(js.prettyPrint(resultats), false);

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

				List<VerticalGroup> vgs = new ArrayList<>();

				divergence_w = 360;
				divergence_h = 200;
				divergence_x = 175;
				divergence_y = 100;

				fip_w = 650;
				fip_h = 375;
				fip_x = 30;
				fip_y = 800;

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

								final Json js = new Json();
								resultats = js.fromJson(ArrayList.class, Resultat.class, jsonFile);

								resultats.add(resultatMap.get(nom));

								jsonFile.writeString(js.prettyPrint(resultats), false);

								tbb_.setName(nom.replace("not_saved", ""));
							}

							tbb_.setTouchable(Touchable.disabled);
							hg_.space(400);
							hg_.addActor(tbb_);


							return true;
						}
					});

					vgs.add(vg);


					Image im_barre = new Image(barre);

					VerticalGroup h_barre_ = new VerticalGroup();
					h_barre_.addActor(im_barre);
					h_barre_.padTop(40);
					vgs.add(h_barre_);
				}

				for (int i = vgs.size() -1; i > 0; i--){

					table.add(vgs.get(i));
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
        scroll_playList.setHeight(Gdx.graphics.getHeight() - 50 - menu_bandeau_h);
        scroll_playList.setPosition(10, 20);
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

		batch.draw(menu_bandeau, 2, menu_bandeau_y, menu_bandeau_w, menu_bandeau_h);
		batch.draw(menu_carres, menu_icons_x3, menu_icons_y, menu_icons_w3, menu_icons_h);

		switch (page){
			case 1 : batch.draw(divergence, divergence_x, divergence_y, divergence_w, divergence_h);
				     batch.draw(fip, fip_x, fip_y, fip_w, fip_h);
				     batch.draw(menu_playlist, menu_icons_x1, menu_icons_y, menu_icons_w1, menu_icons_h);

					 if (options) {
						batch.draw(fond_options, 200, Gdx.graphics.getHeight() - 120 - (Gdx.graphics.getHeight() - 850), Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 850);
						font.getData().setScale(3.2f);
					 }

					 break;
			case 2 : batch.draw(menu_radio, menu_icons_x1, menu_icons_y, menu_icons_w1, menu_icons_h);
				     batch.draw(menu_recherche, menu_icons_x2, menu_icons_y, menu_icons_w2, menu_icons_h);
				     font.getData().setScale(4.1f);
				     font.draw(batch, motif_recherche, 150, Gdx.graphics.getHeight() - 20);

					 if (options) {
						batch.draw(fond_options_haut, 200, Gdx.graphics.getHeight() - 120 -(Gdx.graphics.getHeight() - 200), Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 200);
						font.getData().setScale(3.2f);
						batch.draw(divergence, 		250, Gdx.graphics.getHeight() - 120 - 100,  120, 80);
						batch.draw(fip, 			250, Gdx.graphics.getHeight() - 120 - 230,  120, 80);
						batch.draw(trash, 			265, Gdx.graphics.getHeight() - 120 - 360,  80,  80);
						batch.draw(no_trash, 		265, Gdx.graphics.getHeight() - 120 - 490,  80,  80);
						batch.draw(menu_play, 		265, Gdx.graphics.getHeight() - 120 - 620,  80,  80);
						batch.draw(menu_yt, 		220, Gdx.graphics.getHeight() - 120 - 750,  150, 80);
						batch.draw(menu_horloge, 	265, Gdx.graphics.getHeight() - 120 - 880,  80,  80);
						batch.draw(menu_calendrier, 265, Gdx.graphics.getHeight() - 120 - 1010, 80,  80);

						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 50);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 180);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 310);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 440);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 570);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 700);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 830);
						font.draw(batch, "Afficher : ", 400, Gdx.graphics.getHeight() - 120 - 960);

						batch.draw(case_1, 585, Gdx.graphics.getHeight() - 120 - 100, 80, 80);
						batch.draw(case_2, 585, Gdx.graphics.getHeight() - 120 - 230, 80, 80);
						batch.draw(case_3, 585, Gdx.graphics.getHeight() - 120 - 360, 80, 80);
						batch.draw(case_4, 585, Gdx.graphics.getHeight() - 120 - 490, 80, 80);
						batch.draw(case_5, 585, Gdx.graphics.getHeight() - 120 - 620, 80, 80);
						batch.draw(case_6, 585, Gdx.graphics.getHeight() - 120 - 750, 80, 80);
						batch.draw(case_7, 585, Gdx.graphics.getHeight() - 120 - 880, 80, 80);
						batch.draw(case_8, 585, Gdx.graphics.getHeight() - 120 - 1010, 80, 80);
					 }

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

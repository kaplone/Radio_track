package fr.kaplone.libgdx;

/**
 * Created by kaplone on 12/06/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HttpManager_fip implements HttpResponseListener
{
    private String result;
    private byte[] byteResult;

    Net.HttpRequest request;

    public HttpManager_fip(String url, String httpMethod, String content)
    {
        request = new Net.HttpRequest();
        request.setMethod(httpMethod); //or POST
        request.setContent(content); //you can put here some PUT/GET content
        request.setUrl(url);
        Gdx.net.sendHttpRequest(request, this);
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse)
    {
        if( httpResponse.getStatus().getStatusCode() != 200 )
        {
            //ERROR
            float errorCode = httpResponse.getStatus().getStatusCode();
        }
        else
        {
            result = httpResponse.getResultAsString();

            List<Fip_stream> fip_streams = new ArrayList<>();

            Json json_parser = new Json();
            JsonReader json = new JsonReader();

            JsonValue base = json.parse(result);
            JsonValue steps = base.get("steps");
            JsonValue premier = steps.child();


            if (! premier.getString("embedType").equals("blank")){
                Fip_stream fs = new Fip_stream();
                fs.read(json_parser, premier);
                fip_streams.add(fs);
            }


            JsonValue temp = premier;
            Fip_stream fs_temp;

            List<Resultat> resultats = new ArrayList<>();

            SimpleDateFormat formater_heure = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formater_date = new SimpleDateFormat("dd/MM/yyyy");



            while (temp.next() != null){
                temp = temp.next();
                if (! temp.getString("embedType").equals("blank")){

                    long start = temp.has("start") ? temp.getLong("start") : 0;
                    Date date = new Date(start * 1000);

                    Resultat r = new Resultat();
                    r.setRadio("FIP");
                    r.setHeure(formater_heure.format(date));
                    r.setDate(formater_date.format(date));
                    r.setAuteur(temp.has("performers") ? temp.getString("performers").replace("'", " ") : "");
                    r.setTitre(temp.has("title") ? temp.getString("title").replace("'", " ") : "");
                    r.setItunes(temp.has("path") ? temp.getString("path") : "");
                    r.setYoutube(temp.has("lienYoutube") ? temp.getString("lienYoutube") : "");

                    resultats.add(r);
                }
            }
            Radio_Track.setResultats(resultats);
        }
    }

    @Override
    public void failed(Throwable t)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void cancelled()
    {
        // TODO Auto-generated method stub
    }
}
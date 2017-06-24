package fr.kaplone.libgdx;

/**
 * Created by kaplone on 12/06/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.*;

import java.util.ArrayList;
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

            while (temp.next() != null){
                temp = temp.next();
                if (! temp.getString("embedType").equals("blank")){
                    fs_temp = new Fip_stream();
                    fs_temp.read(json_parser, temp);
                    fip_streams.add(fs_temp);
                }

            }

            List<Resultat> resultats = new ArrayList<>();
            List<Fip_stream> reversed_fs = new ArrayList<>();

            for (int i = fip_streams.size() -1; i >= 0; i--){
                reversed_fs.add(fip_streams.get(i));
            }

            for (Fip_stream f : reversed_fs){
                resultats.add(new Resultat(f));
            }

            System.out.println("Appel de setResultat()");

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
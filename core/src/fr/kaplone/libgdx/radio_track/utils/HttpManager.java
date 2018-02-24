package fr.kaplone.libgdx.radio_track.utils;

/**
 * Created by kaplone on 12/06/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import fr.kaplone.libgdx.radio_track.models.Resultat;

import java.text.SimpleDateFormat;
import java.util.*;

public class HttpManager implements HttpResponseListener
{
    private String result;
    private byte[] byteResult;

    Net.HttpRequest request;

    public HttpManager(String url, String httpMethod, String content)
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
        if( httpResponse.getStatus().getStatusCode() == 200 )
        {
            result = httpResponse.getResultAsString();

            Iterator<String> it = Arrays.asList(result.split("\n")).iterator();

            String s;
            boolean jump = true;
            String out_ = "";
            int skip = 3;

            while (it.hasNext()){

                s = it.next();

                if (s.trim().equals("<div class=\"widget-body\" id=\"antenne\">")){
                    jump = false;

                }
                else if (s.trim().equals("</ul>") && ! jump){
                    jump = true;
                    out_ += s;
                }

                else if (! jump){
                    if (skip > 0){
                        skip --;
                    }
                    else {
                        out_ += s;
                    }

                }
            }

            XmlReader reader = new XmlReader();
            XmlReader.Element root = null;

            root = reader.parse(out_);
            Array<XmlReader.Element> items = root.getChildrenByNameRecursively("div");

            System.out.println("---> parse");

            List<Resultat> resultats = new ArrayList<>();
            Resultat temp = null;

            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

            Date date = new Date();
            String date_ = formater.format(date);

            for (XmlReader.Element child : items)
            {
                System.out.println(child);

                if (child.getAttribute("class").equals("widget-posts-descr hour")){



                        temp = new Resultat();
                        temp.setRadio("DIVERGENCE FM");
                        temp.setHeure(child.getText());

                    }
                    else if (child.getAttribute("class").equals("widget-posts-descr ")){
                        temp.setTitre(child.getText().split(" - ")[0].trim().replace("'", " "));
                        temp.setAuteur(child.getText().split(" - ")[1].trim().replace("'", " "));
                        temp.setDate(date_);
                        resultats.add(temp);
                    }

            }

            fr.kaplone.libgdx.radio_track.app.Radio_Track.setResultats(resultats);
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
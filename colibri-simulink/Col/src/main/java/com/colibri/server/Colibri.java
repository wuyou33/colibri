/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colibri.server;

import com.colibri.message.Header.ContentType;
import com.colibri.message.Header.Header;
import com.colibri.message.Header.Identifier;
import com.colibri.message.Header.StatusCodes;
import com.colibri.message.Validator.Validate;
import com.java.connector.Client;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author codelife
 */
@ServerEndpoint("/chat")
public class Colibri {
    static String sta = "";
    static String temp = "";
    static String addt = "";
    static String obst = "";
    static String addl = "";
    static String obsl = "";
    static String tc = "";
    static String que = "";
    static String qre = "";
    Validate v = new Validate();
    static Set<Session> chatUsers = Collections.synchronizedSet(new HashSet<Session>());
    @OnOpen
    public void handleOpen (Session userSession) throws IOException
    {
        chatUsers.add(userSession);
    }
    @OnMessage
    public void handleMessage (String message, Session userSession) throws IOException
    {
        String msg_id = "";
        //String username = (String) userSession.getUserProperties().get("username");
        if (message.equalsIgnoreCase("DRE"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
                String msg;
                msg = Identifier.DRE + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/TC"+ "<br>";
                breakStatus(msg);
                tc = "";
            }
            else
                breakStatus("Error: Cannot Deregister the connector as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("REMT"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = Identifier.REM + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/temperatureservice"+ "<br>";
            if (addt.equalsIgnoreCase("addt"))
            {
                breakStatus(msg);
                addt = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot remove service as service is not added");
            }
            else
                breakStatus("Error: Cannot Remove service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("OBST"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = "OBST<br>" + Identifier.OBS + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/temperatureservice"+ "<br>";
            if (addt.equalsIgnoreCase("addt"))
            {
                breakStatus(msg);
                obst = "obst";
                sta = "";
            }
            else
                breakStatus("Error: Cannot observe service as service is not added");
            }
            else
                breakStatus("Error: Cannot Observe service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("DETT"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = Identifier.DET + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/temperatureservice"+ "<br>";
            if (obst.equalsIgnoreCase("obst") && addt.equalsIgnoreCase("addt"))
            {
                breakStatus(msg);
                obst = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot dettach service as service is not observed");
            }
            else
                breakStatus("Error: Cannot Dettach service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("GETT"))
        {
            if (tc.contentEquals("TC"))
            {
            if (addt.equalsIgnoreCase("addt"))
            {
                String id = Header.getId();
                Header.setId(id);
                breakStatus("GETT<br>GET" + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/temperatureservice"+ "<br>");
                obst = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot get the service as service is not added");
            }
            else
                breakStatus("Error: Cannot Get the service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("REML"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = Identifier.REM + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/lightservice"+ "<br>";
            if (addl.equalsIgnoreCase("addl"))
            {
                breakStatus(msg);
                addl = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot remove service as service is not added");
            }
            else
                breakStatus("Error: Cannot Remove service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("OBSL"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = "OBSL<br>" + Identifier.OBS + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/lightservice"+ "<br>";
            if (addl.equalsIgnoreCase("addl"))
            {
                breakStatus(msg);
                obsl = "obsl";
                sta = "";
            }
            else
                breakStatus("Error: Cannot observe service as service is not added");
            }
            else
                breakStatus("Error: Cannot Observe service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("DETL"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
                String id = Header.getId();
                Header.setId(id);
            String msg;
            msg = Identifier.DET + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/lightservice"+ "<br>";
            if (obsl.equalsIgnoreCase("obsl") && addl.equalsIgnoreCase("addl"))
            {
                breakStatus(msg);
                obsl = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot dettach service as service is not observed");
            }
            else
                breakStatus("Error: Cannot Dettach service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("GETL"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
            if (addl.equalsIgnoreCase("addl"))
            {
                String id = Header.getId();
                Header.setId(id);
                breakStatus("GETL<br>GET" + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + id + "<br>Date:"+ Header.getDate() +"<br>http://www.colibri-samples.org/lightservice"+ "<br>");
                obsl = "";
                sta = "";
            }
            else
                breakStatus("Error: Cannot get the service as service is not added");
            }
            else
                breakStatus("Error: Cannot Get the service as Technology Connecter is not registered");
        }
        else if (message.equalsIgnoreCase("QRE"))
        {
            if (tc.equalsIgnoreCase("TC"))
            {
            if (que.equalsIgnoreCase("QUE") && qre.equalsIgnoreCase(""))
            {
                String msg;
                msg = Identifier.QRE + "<br>Content-Type: " + ContentType.APPLICATION_RESULT + "<br>Message-Id: random26<br>Reference-Id: random25<br>";
                msg += "{<br>\"head\": { \"vars\": [\"service\"] },<br>\"results\": {<br>\"bindings\": [<br>{<br>\"service\" : { \"type\": \"uri\", \"value\": \"http://www.colibri-samples.org/service1\" },<br>\"identifier\" : { \"type\": \"literal\", \"value\": \"temp_monitoring_17\" }<br>}<br>]<br>}<br>}";
                breakStatus(msg);
            }
            else
                breakStatus("Error: Result without query");
            }
            else
                breakStatus("Error: Cannot send QRE result as Technology Connecter is not registered");
        }
        else
        {
        String[] lines = message.split("<br>");
            for(String ss:lines)
            {
                if (ss.equals("REG"))
                {
                    temp = "REG";
                    tc = "TC";
                }
                if (ss.equals("QUE"))
                {
                    que = "que";
                }
                if (ss.contains("temperatureservice") && addt.equalsIgnoreCase(""))
                    addt = "addt";
                if (ss.contains("lightservice") && addl.equalsIgnoreCase(""))
                    addl = "addl";
            Iterator<Session> iterator = chatUsers.iterator();
            while (iterator.hasNext())
                iterator.next().getBasicRemote().sendText(buildJSONData(ss));
            }
    }
        String status = sta(Header.getRefId());
        breakStatus(status);
    }
    @OnClose
    public void handleClose (Session userSession)
    {
        chatUsers.remove(userSession);
    }

    public String buildJSONData(String message) {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", message).build();
        StringWriter sw = new StringWriter();
        try (JsonWriter jw = Json.createWriter(sw)) {jw.write(jsonObject);}
        return sw.toString();
    }

    private void breakStatus(String message) throws IOException {
        String[] lines = message.split("<br>");
            for(String ss:lines)
            {
                Iterator<Session> iterator = chatUsers.iterator();
                while (iterator.hasNext())
                {
                iterator.next().getBasicRemote().sendText(buildJSONData(ss));
                }
            }
    }

    private String sta(String msg_id) {
        String msg = "";
        msg = Identifier.STA + "<br>Content-Type:" + ContentType.TEXT_PLAIN + "<br>Message-Id:" + Header.getId() + "<br>Reference-Id:" + msg_id + "<br>Date:" + Header.getDate() + "<br>" +StatusCodes.OK + "<br>";
        return msg;
    }
}
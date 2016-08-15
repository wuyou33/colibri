/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.connector;

import com.colibri.message.Header.Header;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author codelife
 */


/**
 * ChatServer Client
 *
 * @author codelife
 */
@ClientEndpoint
public class Connector {
    Session session;
    String abc = "";
    public Connector() throws URISyntaxException, DeploymentException, IOException
    {
        URI uri = new URI("ws://localhost:8080/Col-1.0/chat");
        ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
        
    }
    @OnOpen
    public void processOpen (Session session) throws IOException
    {
        this.session = session;
    }
    @OnMessage
    public void processMessage (String message) throws IOException, URISyntaxException, DeploymentException, SAXException, ParserConfigurationException
    {
        String[] lines = message.split("<br>");
            for(String ss:lines)
            {
                if (Json.createReader(new StringReader(ss)).readObject().getString("message").equalsIgnoreCase("GETL"))
                {
                    abc = Client.getlight(ss, Header.getRefId());
                    sendMessage(abc);
                }
                if (Json.createReader(new StringReader(ss)).readObject().getString("message").equalsIgnoreCase("GETT"))
                {
                    abc = Client.gettemp(ss, Header.getRefId());
                    sendMessage(abc);
                }
                if (Json.createReader(new StringReader(ss)).readObject().getString("message").equalsIgnoreCase("OBST"))
                {
                    abc = Client.obstemp(ss, Header.getRefId());
                    sendMessage(abc);
                }
                if (Json.createReader(new StringReader(ss)).readObject().getString("message").equalsIgnoreCase("OBSL"))
                {
                    abc = Client.obslight(ss, Header.getRefId());
                    sendMessage(abc);
                }
                System.out.println(Json.createReader(new StringReader(ss)).readObject().getString("message"));
            }
    }
    public void sendMessage(String message) throws IOException
    {
        session.getBasicRemote().sendText(message);
    }
}

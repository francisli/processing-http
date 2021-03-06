/**
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, version 3.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 */
package com.francisli.processing.restclient;

import org.apache.http.HttpHost;
import processing.core.PApplet;

/**
 * <p>An HttpRequest object represents a single HTTP request to a server.  Each
 * request is dispatched in its own background thread and is passed along with
 * its response in the responseReceived() callback.</p>
 *
 * @author Francis Li
 * @usage Application
 * @param request HttpRequest: any variable of type HttpRequest
 */
public class HttpRequest extends Thread {
    RESTClient client;
    HttpHost host;
    org.apache.http.HttpRequest request;

    HttpRequest() {
    }

    HttpRequest(RESTClient client, HttpHost host, org.apache.http.HttpRequest request) {
        this.client = client;
        this.host = host;
        this.request = request;
    }

    public void background(HttpResponse response) {
    }

    public void pre(HttpResponse response) {
    }

    /**
     * @exclude
     */
    @Override
    public void run() {
        try {
            if (client.logging) {
                PApplet.println("RESTClient: Connecting to " + host.getHostName() + " on port " + host.getPort());
                PApplet.println("RESTClient: " + request.getRequestLine().toString());
            }
            org.apache.http.HttpResponse httpResponse = client.httpClient.execute(host, request);
            HttpResponse response = new HttpResponse(httpResponse);
            background(response);
            client.put(this, response);
        } catch (Exception e) {
            System.err.println("RESTClient: An error occurred- ");
            e.printStackTrace();;
        }
    }
}

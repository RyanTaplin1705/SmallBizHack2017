package io.github.tjheslin1.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

public class ApacheHttpClient implements HttpClient {

    private final org.apache.http.client.HttpClient httpClient;

    public ApacheHttpClient() {
        httpClient = HttpClientBuilder.create()
                .build();
    }

    public Response execute(Request request) throws IOException {
        System.out.println(request);
        HttpResponse response = httpClient.execute(adaptRequest(request));
        System.out.println(response);

        return adaptResponse(response);
    }

    private HttpUriRequest adaptRequest(Request request) throws IOException {
        GenericHttpUriRequest httpUriRequest = new GenericHttpUriRequest(request.method, adaptUrl(request));
        for (Header header : request.headers) {
            httpUriRequest.setHeader(adaptHeader(header));
        }
        adaptBody(request, httpUriRequest);
        return httpUriRequest;
    }

    private org.apache.http.Header adaptHeader(Header header) {
        return new BasicHeader(header.key, header.value);
    }

    private void adaptBody(Request request, GenericHttpUriRequest httpUriRequest) throws IOException {
        if (!request.body.isEmpty()) {
            try {
                httpUriRequest.setEntity(new StringEntity(request.body));
            } catch (UnsupportedEncodingException e) {
                throw new IOException(format("Unsupported Encoding for request '%s'", request.toString()));
            }
        }
    }

    private Response adaptResponse(HttpResponse response) throws IOException {
        return new Response(adaptStatusCode(response),
                adaptBody(response),
                adaptProtocol(response));
    }

    private URI adaptUrl(Request request) throws IOException {
        try {
            return new URIBuilder(request.url).build();
        } catch (URISyntaxException e) {
            throw new IOException(format("Invalid url '%s'", request.url), e);
        }
    }

    private String adaptProtocol(HttpResponse response) {
        return response.getProtocolVersion().toString().toUpperCase();
    }

    private String adaptBody(HttpResponse response) throws IOException {
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity == null) {
            return "";
        }
        return EntityUtils.toString(httpEntity);
    }

    private int adaptStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private static class GenericHttpUriRequest extends HttpEntityEnclosingRequestBase {

        private final String method;

        private GenericHttpUriRequest(String method, URI uri) {
            this.method = method;
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return method;
        }
    }
}
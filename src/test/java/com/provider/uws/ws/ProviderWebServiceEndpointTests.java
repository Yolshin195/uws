package com.provider.uws.ws;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProviderWebServiceEndpointTests {

    @Test
    void getInformationTest() throws Exception {

        String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:uws="http://uws.provider.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <uws:GetInformationArguments>
                         <password>pwd</password>
                         <username>user</username>
                         
                         <parameters>
                            <paramKey>phone</paramKey>
                            <paramValue>+998917813126</paramValue>
                         </parameters>
                          <parameters>
                            <paramKey>pin</paramKey>
                            <paramValue>123456</paramValue>
                         </parameters>
                                
                         <serviceId>1</serviceId>
                      </uws:GetInformationArguments>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;

        HttpResponse<String> response = sendPost(body);

        assertTrue(response.body().contains("<status>200</status>"));
        assertEquals(200, response.statusCode());
    }

    @Test
    void getInformationByNumberTest() throws Exception {

        String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:uws="http://uws.provider.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <uws:GetInformationArguments>
                         <password>pwd</password>
                         <username>user</username>
                         
                         <parameters>
                            <paramKey>number</paramKey>
                            <paramValue>9990836456205582</paramValue>
                         </parameters>
                          <parameters>
                            <paramKey>pin</paramKey>
                            <paramValue>123456</paramValue>
                         </parameters>
                                
                         <serviceId>1</serviceId>
                      </uws:GetInformationArguments>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;

        HttpResponse<String> response = sendPost(body);

        assertTrue(response.body().contains("<status>200</status>"));
        assertEquals(200, response.statusCode());
    }

    @Test
    void performTransactionTest() throws Exception {

        String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:uws="http://uws.provider.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <uws:PerformTransactionArguments>
                         <password>pwd</password>
                         <username>user</username>
                         <amount>300000</amount>
                         
                         <parameters>
                         	<paramKey>phone</paramKey>
                            <paramValue>+998917813126</paramValue>
                	    </parameters>
                         <parameters>
                         	<paramKey>pin</paramKey>
                            <paramValue>123456</paramValue>
                         </parameters>
                         
                         <serviceId>1</serviceId>
                         <transactionId>15</transactionId>
                         <transactionTime>2022-10-23T16:50:32.748888800</transactionTime>
                      </uws:PerformTransactionArguments>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;

        HttpResponse<String> response = sendPost(body);

        assertTrue(response.body().contains("<status>201</status>"));
        assertEquals(200, response.statusCode());

    }

    @Test
    void performTransactionByNumberTest() throws Exception {

        String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:uws="http://uws.provider.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <uws:PerformTransactionArguments>
                         <password>pwd</password>
                         <username>user</username>
                         <amount>300000</amount>
                         
                         <parameters>
                            <paramKey>number</paramKey>
                            <paramValue>9990836456205582</paramValue>
                	    </parameters>
                         <parameters>
                            <paramKey>pin</paramKey>
                            <paramValue>123456</paramValue>
                         </parameters>
                         
                         <serviceId>1</serviceId>
                         <transactionId>16</transactionId>
                         <transactionTime>2022-10-23T16:50:32.748888800</transactionTime>
                      </uws:PerformTransactionArguments>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;

        HttpResponse<String> response = sendPost(body);

        assertTrue(response.body().contains("<status>201</status>"));
        assertEquals(200, response.statusCode());

    }

    private HttpResponse<String> sendPost(String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/soap-api/service/test?wsdl"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/xml")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

# uws

### Available SOAP services
`
    http://localhost:8080/soap-api
`

### WSDL link
`
    http://localhost:8080/soap-api/uws?wsdl
`

### Run server
```#!bash
    $ ./mvnw spring-boot:run
```

### Run tests
```#!bash
    $ ./mvnw test
```

### Example performTransaction

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
            <transactionId>17</transactionId>
            <transactionTime>2022-10-23T16:50:32.748888800</transactionTime>
        </uws:PerformTransactionArguments>
    </soapenv:Body>
    </soapenv:Envelope>


### Example getInformation

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

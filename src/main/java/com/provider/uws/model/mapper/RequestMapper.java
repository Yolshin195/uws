package com.provider.uws.model.mapper;

import com.provider.uws.GenericParam;
import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.model.dto.RequestDTO;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class RequestMapper {

    public RequestDTO valueOf(PerformTransactionArguments params) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setServiceId(params.getServiceId());

        List<GenericParam> paramList = params.getParameters();

        getPhone(paramList).ifPresent(genericParam ->
                requestDTO.setPhone(genericParam.getParamValue()));

        getNumber(paramList).ifPresent(genericParam ->
                requestDTO.setNumber(genericParam.getParamValue()));

        getPin(paramList).ifPresent(genericParam ->
                requestDTO.setPin(genericParam.getParamValue()));

        return requestDTO;
    }

    private LocalDateTime fromXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDateTime.of(
                xmlGregorianCalendar.getYear(),
                xmlGregorianCalendar.getMonth(),
                xmlGregorianCalendar.getDay(),
                xmlGregorianCalendar.getHour(),
                xmlGregorianCalendar.getMinute(),
                xmlGregorianCalendar.getSecond(),
                xmlGregorianCalendar.getMillisecond()
        );
    }

    private Optional<GenericParam> getPhone(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("phone"))
                .findAny();
    }

    private Optional<GenericParam> getPin(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("pin"))
                .findAny();
    }

    private Optional<GenericParam> getNumber(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("number"))
                .findAny();
    }
}

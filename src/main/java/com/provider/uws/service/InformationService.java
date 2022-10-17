package com.provider.uws.service;

import com.provider.uws.GetInformationArguments;
import com.provider.uws.GetInformationResult;

public interface InformationService {
    GetInformationResult getInformation(GetInformationArguments arguments);
}

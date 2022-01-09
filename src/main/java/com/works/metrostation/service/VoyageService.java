package com.works.metrostation.service;

import com.works.metrostation.dto.VoyageDateTimeDto;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.model.Voyage;

public interface VoyageService {

    Voyage createVoyage(VoyageDto voyageDto);

    VoyageDateTimeDto subwayArrivalTime();

}
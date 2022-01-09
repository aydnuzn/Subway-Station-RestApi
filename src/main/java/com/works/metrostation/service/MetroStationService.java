package com.works.metrostation.service;

import com.works.metrostation.dto.BoardingResponseDto;
import com.works.metrostation.dto.UserDetailsDto;

public interface MetroStationService {

    BoardingResponseDto entranceToSubway(UserDetailsDto userDetailsDto);

}

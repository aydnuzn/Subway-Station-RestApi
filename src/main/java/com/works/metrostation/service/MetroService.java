package com.works.metrostation.service;

import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.model.Metro;

public interface MetroService {

    Metro createMetro(MetroDto metroDto);

    Metro getMetro(Long metroId);

    Metro updateMetro(Long metroId, MetroDto metroDto);

    Metro deleteMetro(Long metroId);

}

package com.educom.restclient.client.service;

import com.educom.restclient.model.Lehre;
import reactor.core.publisher.Flux;

public interface LehreClientService {
       Flux<Lehre> getLehreById(Long symbol);
       Flux<Lehre> getLehreList();
       String saveLehre(Lehre lehre);
       Flux<Lehre> delete(Lehre lehre);
}

package com.educom.restclient.client;

import reactor.core.publisher.Flux;

public interface HttpService<E> extends RequestHeaderHeader<E> {
    String update(Long id, E e);
    String add(E e);
    String delete(Long id);
    Flux<E> findByName(String name);

}

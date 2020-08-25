package com.educom.restclient.client;

import java.util.List;

public interface HttpService<E> extends RequestHeaderHeader<E> {
    String update(Long id, E e);
    String add(E e);
    String delete(Long id);
    List<E> findByName(String name);

}

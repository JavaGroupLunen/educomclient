package com.educom.restclient.client;

import org.springframework.http.HttpHeaders;

public interface RequestHeaderHeader<E> {
    HttpHeaders getHeader();
}

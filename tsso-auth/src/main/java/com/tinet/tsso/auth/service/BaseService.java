package com.tinet.tsso.auth.service;

public interface BaseService<T, ID> {

	T get(ID id);

	Integer create(T t);

	Integer update(T t);

	Integer delete(ID id);

}

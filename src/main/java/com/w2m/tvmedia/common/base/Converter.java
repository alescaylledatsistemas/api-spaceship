package com.w2m.tvmedia.common.base;

import java.util.Collection;
import java.util.List;

import lombok.NonNull;

public interface Converter<T, V> {

	@NonNull
	V convertEntityToDTO(T t);

	@NonNull
	List<V> convertEntityToDTO(Collection<T> tList);

	@NonNull
	T convertDTOToEntity(V v);

	@NonNull
	List<T> convertDTOToEntity(Collection<V> vList);

	@NonNull
	T updateEntityFromDTO(T t, V v);

}

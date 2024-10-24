package com.w2m.tvmedia.common.base;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
public abstract class AbstractConverter<T, V> implements Converter<T, V> {

	private final Mapper mapper;
	private final Class<T> tClass;
	private final Class<V> vClass;

	public AbstractConverter(Mapper mapper, Class<T> tClass, Class<V> vClass) {
		this.mapper = mapper;
		this.tClass = tClass;
		this.vClass = vClass;
	}

	@Override
	@NonNull
	public V convertEntityToDTO(T t) {
		return mapper.map(t, vClass);
	}

	@Override
	@NonNull
	public List<V> convertEntityToDTO(Collection<T> tList) {
		return tList.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
	}

	@Override
	@NonNull
	public T convertDTOToEntity(V v) {
		return mapper.map(v, tClass);
	}

	@Override
	@NonNull
	public List<T> convertDTOToEntity(Collection<V> vList) {
		return vList.stream().map(this::convertDTOToEntity).collect(Collectors.toList());
	}

	@Override
	@NonNull
	public T updateEntityFromDTO(T t, V v) {
		mapper.map(v, t);
		return t;
	}

}

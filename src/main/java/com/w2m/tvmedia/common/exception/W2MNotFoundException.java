package com.w2m.tvmedia.common.exception;

import com.w2m.tvmedia.common.base.W2MEntity;

public class W2MNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 731233614466488681L;

	private final W2MEntity notFoundEntity;
	private final Long notFoundId;

	public W2MNotFoundException(W2MEntity notFoundEntity, Long notFoundId) {
		super(String.format("Entity %s with id %s not found", notFoundEntity,
				notFoundId != null ? notFoundId : "unknown"));
		this.notFoundEntity = notFoundEntity;
		this.notFoundId = notFoundId;
	}

	public W2MEntity getNotFoundEntity() {
		return notFoundEntity;
	}

	public Long getNotFoundId() {
		return notFoundId;
	}

}

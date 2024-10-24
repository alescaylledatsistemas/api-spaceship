package com.w2m.tvmedia.common.config;

import java.util.Collections;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public DozerBeanMapper dozerBean() {
		List<String> mappingFiles = Collections.singletonList("dozer_mapper.xml");
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}

}

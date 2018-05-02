/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ValidateUtil {
	
	public static Map<String,String> validAll(Object obj){
		Map<String, String> map = new HashMap<String, String>();
		LocalValidatorFactoryBean localValidatorFactoryBean = ApplicationContextUtil.getBean("validatorFactory",LocalValidatorFactoryBean.class);
		Validator v = localValidatorFactoryBean.getValidator();
		Set<ConstraintViolation<Object>> set = v.validate(obj);
		Iterator<ConstraintViolation<Object>> it = set.iterator();
		while(it.hasNext()){
			ConstraintViolation<Object> tmp = it.next();
			map.put(tmp.getPropertyPath().toString(), tmp.getMessage());
		}
		return map;
	}
}

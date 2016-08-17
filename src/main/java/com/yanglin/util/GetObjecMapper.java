package com.yanglin.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @desc 类功能描述：
 * @author devuser
 * @createTime 2016年8月15日 上午11:36:22
 *
 * @version V2.0
 */
public class GetObjecMapper {
    public static ObjectMapper getObjectMapper() {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
        
    }
}

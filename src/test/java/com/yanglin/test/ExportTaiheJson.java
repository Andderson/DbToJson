package com.yanglin.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yanglin.service.UserServiceI;

/**
 * @desc 类功能描述：
 * @author devuser
 * @createTime 2016年8月15日 上午11:32:36
 *
 * @version V2.0
 */
public class ExportTaiheJson {

    private ApplicationContext ac = null;
    private UserServiceI userServiceI;    
    
    @Before
    public void before(){
         //使用"spring-context.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
         ac = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml","spring-mybatis.xml"});
         //从Spring容器中根据bean的id取出我们要使用的userService对象
         userServiceI = (UserServiceI) ac.getBean("userService");
    }
    
    @Test
    public void exportYingDa(){
        
        
        
        
    }
}

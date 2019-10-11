package com.it.shiro.util;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
/*         anon:表示可以匿名使用。
           authc:表示需要认证(登录)才能使用，没有参数
           roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
           perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
           rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
           port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
           authcBasic：没有参数表示httpBasic认证
           ssl:表示安全的url请求，协议为https
           user:当登入操作时不做检查
           */

        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthc");
        shiroFilterFactoryBean.setSuccessUrl("/home/index");

        filterChainDefinitionMap.put("/*", "anon");
        filterChainDefinitionMap.put("/authc/index", "authc");
        filterChainDefinitionMap.put("/authc/admin", "roles[admin]");
        filterChainDefinitionMap.put("/authc/renewable", "perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/removable", "perms[Delete]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS); // 散列次数
        return hashedCredentialsMatcher;
    }

    @Bean
    public EnceladusShiroRealm shiroRealm() {
        EnceladusShiroRealm shiroRealm = new EnceladusShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
        return shiroRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }
}

package br.jus.tse.postagem.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public class JpaConfig {
    @Bean
    public DataSource getDataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup("");
    }
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        
        return jpaTransactionManager;
    }
    
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    @Bean
    public Properties jpaProperties() {
        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "none");
        props.put("hibernate.show_sql"    , "true");
        props.put("hibernate.dialect", "org.hibernate.dialect.Oracle12Dialect");
     
        return props;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lcEntityManFactory = new LocalContainerEntityManagerFactoryBean();
        
        lcEntityManFactory.setDataSource(null);
        lcEntityManFactory.setJpaVendorAdapter(jpaVendorAdapter());
        lcEntityManFactory.setPackagesToScan("br.jus.tse.postagem");
        lcEntityManFactory.setJpaProperties(jpaProperties());
        
        return lcEntityManFactory;
        
        
    }
}

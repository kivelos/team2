package dev.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Properties;

@Configuration
public class DataSourceHibernateConfig {
    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://team6new.c937x7bxd7wg.eu-central-1.rds.amazonaws.com:3306/"
                                       + "team6?verifyServerCertificate=false&allowPublicKeyRetrieval=true"
                                       + "&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&"
                                       + "serverTimezone=UTC");
        driverManagerDataSource.setUsername("teamuser");
        driverManagerDataSource.setPassword("teamuser111999");
        return driverManagerDataSource;
    }

    @Bean
    public LocalSessionFactoryBean mySessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setAnnotatedClasses(
                dev.java.db.model.Candidate.class,
                dev.java.db.model.CandidateExperience.class,
                dev.java.db.model.Skill.class,
                dev.java.db.model.User.class,
                dev.java.db.model.CandidateState.class,
                dev.java.db.model.Vacancy.class,
                dev.java.db.model.Responsibility.class,
                dev.java.db.model.Interview.class,
                dev.java.db.model.Role.class,
                dev.java.db.model.Requirement.class,
                dev.java.db.model.FeedbackState.class,
                dev.java.db.model.SuitableState.class,
                dev.java.db.model.CandidateFeedback.class,
                dev.java.db.model.FeedbackDetails.class,
                dev.java.db.model.CVFeedback.class,
                dev.java.db.model.TMFeedback.class,
                dev.java.db.model.HRFeedback.class,
                dev.java.db.model.DevFeedback.class,
                dev.java.db.model.SuitableState.class
        );
        Properties properties = new Properties();
        properties.put("hibernate.dialect", org.hibernate.dialect.MySQLDialect.class);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.enable_lazy_load_no_trans", true);
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
}

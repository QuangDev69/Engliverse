package learn.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // Indicates that this class contains Spring configurations.
@EnableTransactionManagement // Enables Spring's annotation-driven transaction management.
@MapperScan("learn.mapper") // Scans the specified package for MyBatis mapper interfaces.

public class RootConfig {

    // Configures the DataSource bean.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/testdb"); // Database URL.
        dataSource.setUsername("root"); // Database username.
        dataSource.setPassword("123456"); // Database password.
        return dataSource;
    }

    // Configures the TransactionManager bean.
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // Configures the SqlSessionFactory bean.
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource); // Sets the dataSource for MyBatis.

        // Configures the path to MyBatis mapper XML files if they are used.
        sessionFactory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
        );

        // Configures the package containing entity classes.
        sessionFactory.setTypeAliasesPackage("learn.model");

        return sessionFactory.getObject();
    }

}

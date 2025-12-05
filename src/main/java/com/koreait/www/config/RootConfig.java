package com.koreait.www.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@MapperScan(basePackages = {"com.koreait.www.repository"})
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@Configuration
public class RootConfig {
	
	@Autowired
	ApplicationContext applicationContext;

	// @value properties 파일의 값을 주입
	@Value("${db.driverClassName}")
	private String driverClassName;
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	
	// value 어노테이션을 사용하여 파일을 읽을 수 있도록 지원하는 Bean 설정
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public DataSource dataSource() {
		// jdbcDriver, url, username, password
		HikariConfig hikariConfig = new HikariConfig();
		
		// 필수 사항
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		
		// -- 여기서부터는 hikari 추가설정
		// 최대 커넥션 개수
		hikariConfig.setMaximumPoolSize(5);
		// 최소 유효 커넥션 개수(max와 같은 갯수로 설정)
		hikariConfig.setMinimumIdle(5);
		hikariConfig.setConnectionTestQuery("SELECT now()");
		hikariConfig.setPoolName("springHikariCP");
		
		// ---- 여기서부터는 추가설정
		// cachePrepStmts : cache 사용여부 설정
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		// prepStmtsCacheSize : mysql 드라이버가 한 연결당 cache size : 250~500사이 권장
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSize", "250");
		// connection 당 캐싱할 preparedStatement의 개수 지정 옵션 : default 256
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSqlLimit", "true");
		// mysql 서버에서 최신 이슈가 있을 경우 지원을 받을 것인지 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*.xml"));
		
		// mybatisConfig.xml => 추가옵션 설정
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatisConfig.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}

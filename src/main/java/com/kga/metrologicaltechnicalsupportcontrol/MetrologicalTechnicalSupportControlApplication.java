package com.kga.metrologicaltechnicalsupportcontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:custom_en.properties")
/**Также обратите внимание, что вариант JavaConfig не настраивает пакет явно, потому что пакет аннотированного класса используется по умолчанию. Чтобы настроить пакет для сканирования, используйте один из basePackage…атрибутов @Enable${store}Repositoriesаннотации репозитория, относящегося к хранилищу данных.*/
/*@EnableJpaRepositories(	entityManagerFactoryRef= "")*/
/*@EnableTransactionManagement*/
//@EntityScan(basePackages = "com.kga.metrologicaltechnicalsupportcontrol")
public class MetrologicalTechnicalSupportControlApplication implements CommandLineRunner {//

    @Autowired
    ApplicationContext context ;


    /*@Autowired
    TechObjectRepository techObjectRepository;*/


    /*@Autowired
    DataSource dataSource;*/

    /*@Autowired(required=true)
    private EntityManager entityManagerFactory;*/

    /*@Bean
    public EntityManager entityManager() {
        return entityManagerFactory().getObject().createEntityManager();
    }*/



   /* @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/mtsc");
        dataSource.setUsername("mtsc");
        dataSource.setPassword("mtsc");
        return dataSource;
        //EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        //return builder.setType(EmbeddedDatabaseType.H2).build();
    }*/

    /*@Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("com.kga.metrologicaltechnicalsupportcontrol");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }*/

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        //em.setDataSource(dataSource());
        //em.setPackagesToScan("com.kga.metrologicaltechnicalsupportcontrol");
        return em;
    }*/


    //@Bean
    /*public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }*/

    //@Autowired
    //TechObjectRepository techObjectRepository;

    /*@Autowired
    DataSource dataSource;

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        return sessionFactory;
    }*/

    /*@Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return ;
    }*/
    /*@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }



    @Bean
    //@ConfigurationProperties("spring.datasource")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }*/

    /*@Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }*/

    @Override
    public void run(String... args){
        String[] beans = context.getBeanDefinitionNames();
        for(String bean:beans){
            System.out.println("Bean name: " + bean);
            /*Object object = context.getBean(bean);
            System.out.println( "Bean object:" + object);*/
        }
        //ConfigPeriod configPeriod = context.getBean("configPeriod", ConfigPeriod.class);
        //System.out.println(MarkComponent.NEED.getDescription());
        //MessageSource messageSource = context.getBean("messageSource", MessageSource.class);
        //System.out.println("run:"+messageSource.getMessage("vmiIntervalUnits.year",null, new Locale("ru")));
        //Locale.ru
    }

    public static void main(String[] args) {
        SpringApplication.run(MetrologicalTechnicalSupportControlApplication.class, args);
    }

}

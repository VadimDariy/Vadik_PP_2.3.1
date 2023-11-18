package web.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/vadim");
        dataSource.setUsername("root");
        dataSource.setPassword("19nastena79");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("web.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(addProperties());

        return em;
    }

    Properties addProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.connection.characterEncoding", "utf8");
        properties.setProperty("hibernate.connection.CharSet", "utf8");
        properties.setProperty("hibernate.connection.useUnicode", "true");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

/*
         Задача этого класса заключается в том, что бы создать и сконфигурировать объект "transactionManager"
    класса "PlatformTransactionManager", содержащий в себе все настройки для подключания к базе данных, и управления
    транзакциями с использованием Framework Hibernate в данном приложении.

    ИТАК НАЧНЁМ:

    @Configuration - указывает, на то, что класс содержит конфигурацию для создания бинов, которые Spring по
                     необходимости будет внедрять в рамках текущего проекта и заменяет XML-файлы для настройки
                     Spring-приложений.

    @EnableTransactionManagement - включает управление транзакциями в приложении, позволяя Spring автоматически
                                   обрабатывать транзакции для методов внутри класса, помеченного этой аннотацией.

    @ComponentScan(value = "web") - указывает область сканирования компонентов Spring в рамках заданного пакета
                                    (в данном случае "web").

----------------------------------------------------------------------------------------------------------------------

 1. ОПИСАНИЕ МЕТОДА "public DataSource dataSource()": - возвращает объект параметров для подключения Spring к БД MySQL

    1.1 DriverManagerDataSource dataSource = new DriverManagerDataSource(); - создаём объект "dataSource" типа
                                    DriverManagerDataSource, который является реализацией интерфейса DataSource в
                                    контексте Spring Framework. Будем использовать его для настройки подключения к БД,
                                    такой как URL, имя пользователя и пароль.

    1.2 dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); - В этой строке мы устанавливаем имя класса драйвера
                                    "JDBC" для БД MySQL, что бы Spring понимал какой драйвер ему загружать для установки
                                    соединения с БД MySQL.

    1.3  dataSource.setUrl("jdbc:mysql://localhost:3306/vadim"); - в этой строке мы устанавливаем адрес (Url) для
                                    соеденения с БД MySQL.(локальный сервер mysql на порту 3306 и БД с именем "vadim")

    1.4 dataSource.setUsername("root"); - устанавливает логин (имя пользователя) для соединения с БД MySQL ("root").

    1.5 dataSource.setPassword("19nastena79"); - устанавливает пароль для соединения с БД MySQL ("19nastena79").

    1.6 return dataSource; - эта строка возвращает объект dataSource, содердащий в себе конфигурационные параметры для
                             установления соединения с базой данных MySQL:
                                      а) имя класса драйвера "JDBC" для БД MySQL;
                                      б) адрес (Url) для соеденения с БД MySQL;
                                      в) логин (имя пользователя) для соединения с БД MySQL;
                                      г) пароль для соединения с БД MySQL;

----------------------------------------------------------------------------------------------------------------------

 2. ОПИСАНИЕ МЕТОДА LocalContainerEntityManagerFactoryBean entityManagerFactory() - возвращает объект содержащий в себе
                    параметры для подключения Spring к БД MySQL, а так же настройки Hibernate.

    2.1  LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

    2.2 em.setDataSource(dataSource()); - устанавливаем в объект "em" параметры для соединения с БД MySQL из объекта
                                          "DataSource", который возвращает в параметрах метод "dataSource()"

    2.3 em.setPackagesToScan("web.model"); - устанавливаем в объект "em" область сканирования  сущностей (Entity) JPA
                                             в пределах пакета "model" внутри пакета "web".

    2.4 JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); - Под капотом этого объекта содержатся
                                          настройки и параметры, специфичные для Hibernate, которые позволяют Spring
                                          использовать Hibernate в качестве поставщика JPA.

    2.5  em.setJpaVendorAdapter(vendorAdapter); - указывает Spring использовать Hibernate в качестве поставщика JPA при
                                                 создании объекта "em", что включает соответствующие настройки для работы
                                                 с Hibernate в рамках JPA.

    2.6  em.setJpaProperties(addProperties()); - устанавливаем в объект "em" параметры настроики Framework Hibernate
                                                 содержащиеся в объекте "properties", который возвращает метод
                                                 "addProperties()" указанный в качастве аргумента данного метода.

    2.7 return em; - возвращает объект содержащий в себе:
                             - объект "dataSource", содержащий в себе параметры для подключения к БД MySQL;
                             - объект "vendorAdapter", предоставляет основные настройки Hibernate:
                                                          а) Настраивает поставщика JPA для конкретной БД;
                                                          б) Настраивает вывод в логи SQL-запросы, выполняемые JPA;
                                                          в) Настройка автоматической генерирации DDL-скриптов;
                                                          г) Настройка действий к БД при запуске приложения;
                                                          д) Настройка стратегии именования БД (таблиц, столбцов и т.д.)
                             - объект "properties" с дополнительными, тонкими настройками Hibernate:
                                                          а) hbm2ddl - состояние схемы базы данных;
                                                          б) "org.hibernate.dialect.MySQL5Dialect" - диалект MySQL;
                                                          в) кодировку символов UTF-8;
                                                          г) использование кодировки Unicode;
----------------------------------------------------------------------------------------------------------------------

 3. ОПИСАНИЕ МЕТОДА "Properties addProperties()"

    3.1 Properties properties = new Properties(); - создает объект для хранения пар ключ-значение, который используется
                                                    для настройки и конфигурации различных компонентов приложения.

    3.2 properties.setProperty("hibernate.hbm2ddl.auto", "update"); - значение "update" указывает Hibernate на
                                                    автоматическое обновление схемы базы данных при изменении модели
                                                    данных, где "hibernate.hbm2ddl.auto" - ключ (наименование свойства),
                                                    а "update" - значение, устанавливаемое для этого свойства.

    3.3 properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); - устанавливает свойство
                                                    "hibernate.dialect" в значение "org.hibernate.dialect.MySQL5Dialect",
                                                    что указывает Hibernate на использование диалекта MySQL при
                                                    взаимодействии с базой данных.

    3.4 properties.setProperty("hibernate.connection.characterEncoding", "utf8"); - устанавливает свойство
                                                    "hibernate.connection.characterEncoding" в значение "utf8", что
                                                    задает кодировку символов UTF-8 для подключения к базе данных через
                                                    Hibernate.

    3.5 properties.setProperty("hibernate.connection.CharSet", "utf8"); - устанавливает свойство
                                                    "hibernate.connection.CharSet" в значение "utf8", что также задает
                                                    кодировку символов UTF-8 для подключения к базе данных через Hibernate.

    3.6 properties.setProperty("hibernate.connection.useUnicode", "true"); - При установке свойства
                                                    "hibernate.connection.useUnicode" в значение "true", Hibernate будет
                                                    использовать Unicode при взаимодействии с базой данных. Это
                                                    обеспечивает поддержку широкого спектра символов, включая многие
                                                    международные символы, и обеспечивает корректное отображение текста,
                                                    написанного на различных языках.

    3.7 return properties; - Этой строкой возвращаем объект "properties" который в себе содержит настройки параметров:
                                                    а) hbm2ddl - состояние схемы базы данных;
                                                    б) "org.hibernate.dialect.MySQL5Dialect" - диалект MySQL;
                                                    в) кодировку символов UTF-8;
                                                    г) использование кодировки Unicode;

----------------------------------------------------------------------------------------------------------------------

 4. ОПИСАНИЕ МЕТОДА "PlatformTransactionManager transactionManager()":

    4.1 JpaTransactionManager transactionManager = new JpaTransactionManager(); - создаём объект, который будет
                                              обеспечивать контроль за транзакциями в приложении, обеспечивая их начало,
                                              фиксацию (commit)  и откат (rollback), обеспечивая целостность
                                              данных в приложении. Другими словами это дипетчер, который контролирует
                                              выполнение транзакций, взаимодействует с менеджером сущностей JPA и
                                              управляет транзакциями, обеспечивая их атомарность, согласованность,
                                              изолированность и устойчивость (ACID-свойства).

    4.2 transactionManager.setEntityManagerFactory(entityManagerFactory().getObject()); - с помощью установки как
                                              аргумента мотода entityManagerFactory() и вызова на нём метода getObject(),
                                              мы возвращаем объект "EntityManagerFactory" связанным с настройками
                                              подключения к базе данных и другими параметрами. Далее мы устанавливаем с
                                              помощью метода setEntityManagerFactory() содержание объекта
                                              "EntityManagerFactory" в объект "transactionManager" для управления
                                              транзакциями в приложении.

    4.3 return transactionManager; - этой строкой мы возвращаем готовый и настроеннй объект "PlatformTransactionManager"
                                     содержащий в себе все настройки для подключания к базе данных, и управления
                                     транзакциями с использованием Framework Hibernate в моём приложении.

------------------------------------------------------------------------------------------------------------------------

 5. ОПИСАНИЕ МЕТОДА PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor():

      5.1 return new PersistenceExceptionTranslationPostProcessor(); - в этой строке мы создаём объект типа
                PersistenceExceptionTranslationPostProcessor из Spring Framework и возвращаем его вызывающему методу.
                Данный объект предоставляет механизм перехвата исключени, связанные с доступом к базе данных, и
                конвертирует их в исключения Spring (DataAccessException/Исключение доступа к данным). Это упрощает
                обработку исключений при работе с базой данных в контексте Spring-приложения, плюс ко всему сокращает
                нагромождение дополнительного кода и способствует более чистому и структурированному коду.

------------------------------------------------------------------------------------------------------------------------

            Что такое JPA на примере воды:

    Вода (данные в базе данных): Вода - это данные, которые вы хотите сохранить и получить из резервуара (базы данных).

    Бутылка (Entity): Бутылка представляет объект, который вы хотите сохранить в базе данных. Она может быть использована
                      для хранения воды (данных) и имеет свои характеристики, такие как форма, размер и цвет.

    Банка (Таблица в базе данных): Банка представляет собой таблицу в базе данных. В ней можно размещать несколько
                                   бутылок (Entities). Каждая бутылка имеет свое место в банке, как и каждая запись в
                                   таблице базы данных.

    JPA (Java Persistence API): JPA - это механизм, который обеспечивает стандартизированный способ сохранения,
                                извлечения и управления бутылками (Entities) в и из банки (Таблицы) в базе данных. Он
                                предоставляет интерфейс (как насос или магистраль), который позволяет вам
                                взаимодействовать с бутылками (Entities) без прямого взаимодействия с банкой (Таблицей).
                                Нам не нужно беспокоиться о том, как именно наши бутылки хранятся в банке; мы можете
                                использовать стандартизированный подход для работы с нашими данными.



                                   */
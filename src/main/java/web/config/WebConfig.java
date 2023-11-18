package web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("web")
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        registry.viewResolver(resolver);
    }
}

/*
 1.      Создадим класс "WebConfig" предназначенный для настройки шаблонизации в формировании ответа на запрос
    пользователя(браузера) в веб-приложении с использованием Thymeleaf(шаблонизатора). Для этого реализуем в нашем классе
    интерфейс WebMvcConfigurer из Framework Spring, который предоставляет абстракцию, которая позволит нам переопределить
    и настроить основные параметры конфигурации Spring MVC
    Укажем с помощью аннотации, что наш класс конфигурационный и поддерживается Spring MVC, который будет сканировать
    компоненты в пакете "web":

         @Configuration: Обозначает, что класс является конфигурационным классом Spring.
         @EnableWebMvc: Сообщает Spring о том, что мы хотим использовать MVC и активирует соответствующую конфигурацию.
         @ComponentScan("web"): Указывает Spring на пакет, в котором нужно искать компоненты.

 2.      private final ApplicationContext applicationContext: Эта переменная предоставляет доступ к контексту приложения,
                                                              которая служит для настройки Thymeleaf - шаблонизатора.

------------------------------------------------------------------------------------------------------------------------

 3.     public WebConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
                                  Конструктор инициализирует переменную "applicationContext".
------------------------------------------------------------------------------------------------------------------------

 4.    @Bean: Аннотация, которая указывает "Spring", что этот метод должен быть рассмотрен как определение бина,
                               управляемого контейнером "Spring".

    4.1 public SpringResourceTemplateResolver templateResolver(): Объявляем метод с именем "templateResolver", который
                               возвращает объект "SpringResourceTemplateResolver".

    4.2 SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();: Создаём экземпляр
                               SpringResourceTemplateResolver.

    4.3 templateResolver.setApplicationContext(applicationContext);: Устанавливаем контекста приложения для резольвера
                               шаблонов, который будет предоставлять доступ к различным ресурсам приложения.

    4.4 templateResolver.setPrefix("/WEB-INF/pages/");: Устанавливаем префикса для разрешения путей к шаблонам. В данном
                               случае, шаблоны будут сканироваться в папке "/WEB-INF/pages/".

    4.5 templateResolver.setSuffix(".html");: Устанавливаем суффикс для разрешения имен файлов шаблонов. В данном случае,
                               ожидаем, что шаблоны будут файлами с расширением ".html".

    4.6 templateResolver.setCharacterEncoding("UTF-8");: Установка кодировки символов для шаблонов.

    4.7 return templateResolver;: Возвращаем сконфигурированный объекта SpringResourceTemplateResolver в
                               качестве бина для управления контейнером Spring.
------------------------------------------------------------------------------------------------------------------------

     @Bean
 5.  public SpringTemplateEngine templateEngine() {
                                 этот метод использует аннотацию @Bean, это указывет Spring о том, что этот метод будет
                                 управляться контейнером Spring. Т.е. если данному приложению будет необходим объект
                                 типа "SpringTemplateEngine", то Spring автоматически создаст объект этого типа и
                                 внедрит его.

    5.1 SpringTemplateEngine templateEngine = new SpringTemplateEngine();
                                 Создаём объект "templateEngine" класса "SpringTemplateEngine" для того что бы в него
                                 установить настройки объекта "templateResolver" класса "SpringResourceTemplateResolver"
                                 и автоматический компиляторор выражений "Spring Expression Language"

    5.2 templateEngine.setTemplateResolver(templateResolver());
                                 Эта строка устанавливает объект templateResolver, который предоставляет шаблоны для
                                 шаблонизатора Thymeleaf, в SpringTemplateEngine.

    5.3 templateEngine.setEnableSpringELCompiler(true);
                                 Эта строка устанавливает флаг EnableSpringELCompiler в true для SpringTemplateEngine. В
                                 контексте Thymeleaf и Spring Expression Language (Spring EL/Язык весенних выражений),
                                 это означает активацию компилятора Spring Expression Language.
                                 Spring EL Compiler - это механизм, который компилирует выражения Spring EL в байт-код,
                                 что улучшает производительность выполнения этих выражений. Установка
                                 EnableSpringELCompiler(true) включает этот компилятор, а false - выключает.
                                 Т.е. этой строкой мы показываем, что мы включаем использование компилятора Spring EL
                                 для нашего экземпляра SpringTemplateEngine.

    5.4 return templateEngine;  Тут возвращаем экземпляр "templateEngine" класса "SpringTemplateEngine", настроенный
                                автоматическим компилятором выражений "Spring Expression Language" в байт-кот

    }

------------------------------------------------------------------------------------------------------------------------

6.      Переопределим метод "configureViewResolvers()" из интерфейса "WebMvcConfigurer" и создадим в нем свою
    реализацию для инициализации объекта Thymeleaf:

    6.1   @Override
          public void configureViewResolvers(ViewResolverRegistry registry):
                               этот метод принимает в качестве аргумента объект ViewResolverRegistry, который
                                предоставляет средства для регистрации и настройки различных резолверов представлений в
                               Spring MVC. Внутри тела метода создается объект resolver
                               (в данном случае, ThymeleafViewResolver), который настраивается и затем регистрируется с
                               помощью метода registry.viewResolver(resolver). Зарегистрированный резолвер будет
                               использоваться для разрешения логических имен представлений в конкретные представления
                               при обработке HTTP-запросов в приложении.




    6.2  ThymeleafViewResolver resolver = new ThymeleafViewResolver(); Создаем экземпляр ThymeleafViewResolver,
                               который будет выполнять следующие действия:
                               а) Клиент делает HTTP - запрос;
                               б) Запрос попадает в центральный контроллер DispatcherServlet Spring MVC по управлению
                                  запросами. Далее по типу URL- запроса подбирает соответствующий тип контроллера,
                                  который обработает данный запрос и вернёт логическое имя представления;
                               в) Далее ThymeleafViewResolver разрешает(конвертирует) логическое имя в фактический путь
                                  к результирующему HTML- шаблону(файлу), выбирает его и возвращает его обратно
                                  клиенту(браузеру) в качестве ответа на HTTP-запрос, в виде отображения в браузере
                                  пользователя.
            ThymeleafViewResolver - это стандартный класс в библиотеке Thymeleaf, предназначенной для работы с шаблонами
                                    в Java-приложениях. Он является частью инфраструктуры Spring Framework для
                                    интеграции с Thymeleaf.

    6.3  resolver.setTemplateEngine(templateEngine()); - данный метод у себя в параметрах в качестве аргумента принимает
                                метод "templateEngine()", который возвращает объект типа "SpringTemplateEngine" -
                                - шаблонизатор для обработки шаблонов Thymeleaf. Далее метод setTemplateEngine() уже
                                устанавливает конкретный объект типа "SpringTemplateEngine"(шаблонизатор) в объект
                                класса "ThymeleafViewResolver" тем самым связывая шаблонизатор с резолвером
                                представлений при обработке HTTP-запросов. Теперь у нас связаны между собою два
                                компонента - резолвер представлений (ThymeleafViewResolver) и шаблонизатор
                                (SpringTemplateEngine).

    6.4  resolver.setCharacterEncoding("UTF-8"); - устанавливает кодировку символов для представлений, которые будут
                                обработаны ThymeleafViewResolver ("UTF-8").

    6.5  resolver.setContentType("text/html; charset=UTF-8"); - устанавливает в объект resolver контент типа "text/html"
                                 для ответа в протаколе http в кодировке UTF-8. Это говорит о том, что возвращаемый
                                 ответ будет представлен в виде html-документа, для которого утановленна кодировка UTF-8
                                 для правильного отображения и обработки символов данного документа в браузере.

    6.6  registry.viewResolver(resolver); - в этом методе параметр resolver представляет собою объект, отвечающий за
                                 сопоставления конкретных логических имен с конкретными готовыми шаблонами, в данном
                                 случае это html - шаблоны. Далее он регестрируется с помощью метода "viewResolver()" в
                                 экземпляр "registry" класса "ViewResolverRegistry", который будет отвечать за разрешения
                                 логических имён представлений в конкретные представления при обработке HTTP-запросов в
                                 приложении Spring MVC.

------------------------------------------------------------------------------------------------------------------------

    7.   Резольверы предоставляют механизм, который сопоставляет логические имена представлений с фактическими ресурсами
    шаблонами в приложении. Когда контроллер возвращает логическое имя представления, резольвер берет на себя задачу
    найти соответствующий ресурс и вернуть его для дальнейшего формирования ответа клиенту.

    8.   В данном коде, метод configureViewResolvers настраивает ThymeleafViewResolver и регистрирует его в
    ViewResolverRegistry. Этот резольвер будет отвечать за разрешение логических имен представлений с использованием
    Thymeleaf, шаблонизатора для создания HTML-страниц.

    9.   По итогу данный класс "WebConfig" выполняет роль конфигурации веб-приложения на базе "Spring MVC". Он
    предоставляет правила для обработки HTTP-запросов, настраивает шаблонизатор "Thymeleaf" для генерации HTML-страниц и
    определяет, какие представления использовать при возврате ответов на запросы.

    Краткое описание функций класса:

         9.1 templateResolver(): // Резолвер шаблонов
                     Определяет правила разрешения шаблонов Thymeleaf, указывая путь к шаблонам, их суффикс и кодировку.

         9.2 templateEngine(): // Шаблонизатор
                     Создает и настраивает шаблонизатор SpringTemplateEngine(Spring Шаблонизатор), использующий ранее
                     настроенный templateResolver(Резолвер шаблонов). Включает Spring Expression Language (Spring EL)
                     Compiler (Компилятор языка выражений Spring) для улучшения производительности выражений.

         9.3 configureViewResolvers(ViewResolverRegistry registry): // настройка и регистрация резольвера
                     Настраивает ThymeleafViewResolver, который будет использоваться для разрешения логических имен
                     представлений в конкретные представления с использованием Thymeleaf.

         Таким образом, этот класс определяет конфигурацию для обработки запросов и генерации HTML-ответов в данном
    веб-приложении.

     */
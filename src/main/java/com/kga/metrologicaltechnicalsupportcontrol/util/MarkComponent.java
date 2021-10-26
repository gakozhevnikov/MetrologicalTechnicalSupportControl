package com.kga.metrologicaltechnicalsupportcontrol.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**Перечисление для меток выполнения операций*/
public enum MarkComponent {
    NEED("mark-component.need"),
    COMPLETE("mark-component.complete"),
    COMPLETE_WITH_COMMENT("mark-component.completedWithComment"),
    NOT_COMPLETE("mark-component.not-complete"),
    NOT_NEED("mark-component.not-need");

    private String value;

    MarkComponent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**Метод возвращающий описание перечисления из файла свойств, получаемое из MessageSource, через его реализацию
     * ReloadableResourceBundleMessageSource, значение в ReloadableResourceBundleMessageSource выбирается по полю value этого Enum.
     * Т.е. если в YEAR("vmiIntervalUnits.year") значение vmiIntervalUnits.year - то поэтому тексту будет искаться нужное значение из файла свойств*/
    public String getDescription() {
        return messageSource.getMessage(value, null, value, null);
    }

    /**Необходим для получения из custom.properties значений. MessageSource - является интерфейсом, его реализация
     * ReloadableResourceBundleMessageSource получает в зависимости от Lоcal значение из файла свойств*/
    private MessageSource messageSource;

    public MarkComponent setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }
    /**Вложенный класс, которым для каждого перечисления присваивается MessageSource*/
    @Component
    public static class EnumValuesInjectionService {

        @Autowired
        private MessageSource messageSource;
        //Inject into bean through static inner class and assign value to enum.

        @PostConstruct
        public void postConstruct() {
            for (MarkComponent level : EnumSet.allOf(MarkComponent.class)) {
                level.setMessageSource(messageSource);
            }

        }
    }
}

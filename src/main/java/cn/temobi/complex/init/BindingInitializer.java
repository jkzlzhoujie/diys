package cn.temobi.complex.init;

import java.util.Date;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class BindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new DateTypeEditor());
        webDataBinder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, true));
        webDataBinder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));
    }
}

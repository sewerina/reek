package com.github.sewerina.reek;

import java.util.ArrayList;

public class RecipientList extends ArrayList<Recipient> {
    public RecipientList() {
        // String[] emailArray = {"rpn77@rpn.gov.ru", "gu_moscov@mchs.gov.ru", "andreyvorobiev@mosreg.ru"};
        add(new Recipient("Департамент Росприроднадзора по Центральному федеральному округу","A@.AA"));
        add(new Recipient("Главное управление МЧС России по г.Москве","B@.BB"));
        add(new Recipient("Губернатор Московской области","C@.CC"));
    }
}

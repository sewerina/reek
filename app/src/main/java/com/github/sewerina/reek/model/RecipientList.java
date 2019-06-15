package com.github.sewerina.reek.model;

import java.util.ArrayList;

public class RecipientList extends ArrayList<Recipient> {
    public RecipientList() {
        // String[] emailArray = {"rpn77@rpn.gov.ru", "gu_moscov@mchs.gov.ru", "andreyvorobiev@mosreg.ru"};
        add(new Recipient("Департамент Росприроднадзора по Центральному федеральному округу","A@.AA"));
        add(new Recipient("Главное управление МЧС России по г. Москве","B@.BB"));
        add(new Recipient("Главное управление МЧС России по Московской области (общий отдел)","С@.СС"));
        add(new Recipient("Губернатор Московской области","D@.DD"));
        add(new Recipient("Общероссийская Общественная организация «Зелёный патруль»","E@.EE"));
    }
}

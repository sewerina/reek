package com.github.sewerina.reek.model;

import java.util.ArrayList;

public class RecipientList extends ArrayList<Recipient> {
    public RecipientList() {
        add(new Recipient("Департамент Росприроднадзора по Центральному федеральному округу","rpn77@rpn.gov.ru"));
        add(new Recipient("Главное управление МЧС России по г. Москве","gu_moscov@mchs.gov.ru"));
        add(new Recipient("Главное управление МЧС России по Московской области (общий отдел)","obshiy_mchs_mo@mail.ru"));
        add(new Recipient("Губернатор Московской области","andreyvorobiev@mosreg.ru"));
        add(new Recipient("Общероссийская Общественная организация «Зелёный патруль»","russia@greenpatrol.ru"));
    }
}

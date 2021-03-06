package com.github.sewerina.reek.model;

import java.util.ArrayList;

public class RecipientList extends ArrayList<Recipient> {
    public RecipientList() {
        add(new Recipient("Межрегиональное Управление Росприроднадзора по г. Москве и Калужской области", "rpn77@rpn.gov.ru"));
        add(new Recipient("Межрегиональное Управление Росприроднадзора по Московской и Смоленской областям", "rpn67@rpn.gov.ru"));
        add(new Recipient("Департамент природопользования и охраны окружающей среды г. Москвы", "depmospriroda@mos.ru"));
        add(new Recipient("Министерство экологии и природопользования Московской области", "minecology@mosreg.ru"));
        add(new Recipient("Губернатор Московской области", "andreyvorobiev@mosreg.ru"));
    }
}

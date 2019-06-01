package com.github.sewerina.reek;

import java.util.ArrayList;

public class ReekKindList extends ArrayList<ReekKind> {
    public ReekKindList() {
        add(new ReekKind("Гарь", R.drawable.gar));
        add(new ReekKind("Запах канализации", R.drawable.canalization));
        add(new ReekKind("Сероводород/Запах тухлых яиц", R.drawable.eggs));
        add(new ReekKind("Зловоние от свалки мусора", R.drawable.garbage));
        add(new ReekKind("Выхлопы от автомобилей", R.drawable.exhaust));
    }
}

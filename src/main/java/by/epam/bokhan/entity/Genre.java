package by.epam.bokhan.entity;

/**
 * Created by vbokh on 24.07.2017.
 */
public enum Genre {
    АВТОБИОГРАФИЯ("Автобиография"), БИЗНЕС("Бизнес"), БИОГРАФИЯ("Биография"), ДЕТЕКТИВ("Детектив"), ДЕТСКАЯ_ЛИТЕРАТУРА("Детская литература"), ДРАМА("Драма"),
    ИНФОРМАЦИОННЫЕ_ТЕХНОЛОГИИ("Информационные технологии"), ИСТОРИЯ("История"), КОМЕДИЯ("Комедия"), КОРОТКАЯ_ИСТОРИЯ("Короткая история"), МИФ("Миф"), ПОЭМА("Поэма"),
    ПРИКЛЮЧЕНИЯ("Приключения"), ПЬЕСА("Пьеса"), РЕЛИГИЯ("Религия"), РОМАН("Роман"), СКАЗКА("Сказка"), ТРАГЕДИЯ("Трагедия"), ФАНТАСТИКА("Фантастика"),
    ЭПОС("Эпос"), ЭССЕ("Эссе");

    private String name;
    Genre(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

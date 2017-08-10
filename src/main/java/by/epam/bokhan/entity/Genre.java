package by.epam.bokhan.entity;

/**
 * Created by vbokh on 24.07.2017.
 */
public class Genre {
    /*АВТОБИОГРАФИЯ(31,"Автобиография"), БИЗНЕС(33,"Бизнес"), БИОГРАФИЯ(32,"Биография"), ДЕТЕКТИВ(36,"Детектив"), ДЕТСКАЯ_ЛИТЕРАТУРА(37,"Детская литература"), ДРАМА(29,"Драма"),
    ИНФОРМАЦИОННЫЕ_ТЕХНОЛОГИИ(38,"Информационные технологии"), ИСТОРИЯ(25,"История"), КОМЕДИЯ(27,"Комедия"), КОРОТКАЯ_ИСТОРИЯ(21,"Короткая история"), МИФ(30,"Миф"), ПОЭМА(39,"Поэма"),
    ПРИКЛЮЧЕНИЯ(34,"Приключения"), ПЬЕСА(24,"Пьеса"), РЕЛИГИЯ(40,"Религия"), РОМАН(1,"Роман"), СКАЗКА(22,"Сказка"), ТРАГЕДИЯ(28,"Трагедия"), ФАНТАСТИКА(35,"Фантастика"),
    ЭПОС(26,"Эпос"), ЭССЕ(23,"Эссе");*/
    private int id;
    private String name;


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        return name != null ? name.equals(genre.name) : genre.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

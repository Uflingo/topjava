package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal Meal, int UserId);

    void delete(int id, int UserId);

    Meal get(int id, int UserId);

    Collection<Meal> getAll(int UserId);
}

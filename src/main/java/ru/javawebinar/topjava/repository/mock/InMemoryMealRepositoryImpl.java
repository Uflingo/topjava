package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> this.save(m,1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Map<Integer, Meal> usersMeals = repository.get(AuthorizedUser.id());
        if (usersMeals == null){
            usersMeals = new ConcurrentHashMap<>();
            repository.put(userId, usersMeals);
        }
        usersMeals.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals != null) {
            repository.get(userId).remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals != null)
            return usersMeals.get(id);
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals != null) {
            return usersMeals
                    .values()
                    .stream()
                    .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }
}


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
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.id())
            repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.id())
            return repository.get(id);
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        Collection<Meal> c = repository
                                .values()
                                .stream()
                                .filter(m -> m.getUserId() == AuthorizedUser.id())
                                .sorted(new Comparator<Meal>() {
                                    @Override
                                    public int compare(Meal o1, Meal o2) {
                                        return o2.getDateTime().compareTo(o1.getDateTime());
                                    }
                                })
                                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(c);
        return c;
    }
}


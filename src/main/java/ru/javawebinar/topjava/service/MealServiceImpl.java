package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!repository.delete(id, userId))
            throw new NotFoundException("Cant find food for this user to delete");
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal == null)
            throw new NotFoundException("Cant find food for user");
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getFilteredByDate(int userId, LocalDate start, LocalDate stop) {
        return getAll(userId)
                .stream()
                .filter(m -> DateTimeUtil.isBetween(m.getDate(), start, stop))
                .collect(Collectors.toList());
    }
}
package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    private MealService service;

    public Collection<MealWithExceed> getAll(int userId, int caloriesPerDay){
        log.info("get all");
        return getWithExceeded(service.getAll(userId), caloriesPerDay);
    }

    public Meal create(Meal meal, int userId){
        return service.save(meal,userId);
    }

    public void delete(int mealId, int userId){
        service.delete(mealId, userId);
    }

    public void update(int mealId, int userId){
        service.save(service.get(mealId, userId), userId);
    }

    public Collection<MealWithExceed> getByDate(int userId, LocalDate startDate, LocalDate stopDate, int caloriesPerDay){
        return getWithExceeded(service.getFilteredByDate(userId, startDate, stopDate), caloriesPerDay );
    }

    public Collection<MealWithExceed> getByTime(int userId, LocalTime startTime, LocalTime stopTime, int caloriesPerDay){
        return MealsUtil.getFilteredWithExceeded(service.getAll(userId),startTime, stopTime,caloriesPerDay );
    }

    public Collection<MealWithExceed> getByDateTime(int userId, LocalDateTime startDT, LocalDateTime stopDT, int caloriesPerDay){
        Collection<Meal> mealForDate = service.getFilteredByDate(userId, startDT.toLocalDate(),stopDT.toLocalDate());
        return MealsUtil.getFilteredWithExceeded(mealForDate,startDT.toLocalTime(), stopDT.toLocalTime(),caloriesPerDay);
    }

}
package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
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

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public Collection<MealWithExceed> getAll(){
        log.info("get all");
        return getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal create(Meal meal){
        return service.save(meal,AuthorizedUser.id());
    }

    public void delete(int mealId){
        service.delete(mealId, AuthorizedUser.id());
    }

    public void update(int mealId){
        service.save(service.get(mealId, AuthorizedUser.id()), AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getByDate(LocalDate startDate, LocalDate stopDate){
        return getWithExceeded(service.getFilteredByDate(AuthorizedUser.id(), startDate, stopDate), AuthorizedUser.getCaloriesPerDay() );
    }

    public Collection<MealWithExceed> getByTime(LocalTime startTime, LocalTime stopTime){
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()),startTime, stopTime,AuthorizedUser.getCaloriesPerDay() );
    }

    public Collection<MealWithExceed> getByDateTime(LocalDateTime startDT, LocalDateTime stopDT){
        Collection<Meal> mealForDate = service.getFilteredByDate(AuthorizedUser.id(), startDT.toLocalDate(),stopDT.toLocalDate());
        return MealsUtil.getFilteredWithExceeded(mealForDate,startDT.toLocalTime(), stopDT.toLocalTime(),AuthorizedUser.getCaloriesPerDay());
    }

}
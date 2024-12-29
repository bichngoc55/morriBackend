package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Schedule;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.ScheduleRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class ScheduleService {
     @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Schedule createSchedule(Schedule schedule) {
        validateScheduleUsers(schedule);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
    }

    public List<Schedule> getSchedulesByDateRange(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByWorkDateBetween(start, end);
    }

    public Schedule updateSchedule(String id, Schedule schedule) {
        Schedule existingSchedule = getScheduleById(id);
        
        if (schedule.getMorningShifts() != null) {
            validateUsers(schedule.getMorningShifts());
            existingSchedule.setMorningShifts(schedule.getMorningShifts());
        }
        
        if (schedule.getAfternoonShifts() != null) {
            validateUsers(schedule.getAfternoonShifts());
            existingSchedule.setAfternoonShifts(schedule.getAfternoonShifts());
        }
        
        if (schedule.getWorkDate() != null) {
            existingSchedule.setWorkDate(schedule.getWorkDate());
        }
        
        if (schedule.getStatus() != null) {
            existingSchedule.setStatus(schedule.getStatus());
        }

        return scheduleRepository.save(existingSchedule);
    }

    private void validateScheduleUsers(Schedule schedule) {
        validateUsers(schedule.getMorningShifts());
        validateUsers(schedule.getAfternoonShifts());
    }

    private void validateUsers(List<User> users) {
        if (users != null) {
            for (User user : users) {
                userRepository.findById(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user.getId()));
            }
        }
    }

}

package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.Schedule;
import com.jelwery.morri.Service.ScheduleService;

@RestController
@RequestMapping("/schedule") 
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Schedule>> getSchedulesByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.createSchedule(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable String id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}

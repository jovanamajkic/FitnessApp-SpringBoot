package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.ActivityTracker;
import com.example.fitnessapp.models.requests.ActivityTrackerRequest;
import com.example.fitnessapp.services.ActivityTrackerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activity_trackers")
public class ActivityTrackerController {
    private final ActivityTrackerService service;

    public ActivityTrackerController(ActivityTrackerService service) {
        this.service = service;
    }

    @PostMapping()
    public ActivityTracker insert(@RequestBody @Valid ActivityTrackerRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @GetMapping("/user/{userId}")
    public Page<ActivityTracker> getByUser(@PathVariable Integer userId, @RequestParam int page, @RequestParam int size, Authentication auth){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return service.findByUser(pageable, userId, auth);
    }

    @GetMapping("/all/{userId}")
    public List<ActivityTracker> getAllByUser(@PathVariable Integer userId, Authentication auth){
        return service.findAllByUser(userId, auth);
    }
}

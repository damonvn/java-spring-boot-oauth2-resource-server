package com.example.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.jobhunter.domain.Job;
import com.example.jobhunter.domain.Subscriber;
import com.example.jobhunter.domain.response.ResultPaginationDTO;
import com.example.jobhunter.service.SubscriberService;
import com.example.jobhunter.util.annotation.ApiMessage;
import com.example.jobhunter.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a subscriber")
    public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber sub) throws IdInvalidException {
        // check email
        boolean isExist = this.subscriberService.isExistsByEmail(sub.getEmail());
        if (isExist == true) {
            throw new IdInvalidException("Email " + sub.getEmail() + " đã tồn tại");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.create(sub));
    }

    @PutMapping("/subscribers")
    @ApiMessage("Update a subscriber")
    public ResponseEntity<Subscriber> update(@RequestBody Subscriber reqSubs) throws IdInvalidException {
        // check id
        Subscriber subsDB = this.subscriberService.findById(reqSubs.getId());
        if (subsDB == null) {
            throw new IdInvalidException("Id " + reqSubs.getId() + " không tồn tại");
        }
        return ResponseEntity.ok().body(this.subscriberService.update(subsDB, reqSubs));
    }

    @GetMapping("/subscribers")
    @ApiMessage("Get subscribers with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllSubscribers(
            @Filter Specification<Subscriber> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(this.subscriberService.fetchAllSubscribers(spec, pageable));
    }
}

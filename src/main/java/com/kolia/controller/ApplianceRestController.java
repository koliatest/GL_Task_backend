package com.kolia.controller;

import com.kolia.dao.UserDao;
import com.kolia.model.Appliance;
import com.kolia.model.User;
import com.kolia.service.ApplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class ApplianceRestController {

    @Autowired
    ApplianceService applianceService;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/appliances/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<Appliance>> listAllAppliances(@PathVariable Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userDao.findByLogin(auth.getName());

        if(currentUser.getId() != id) {
            return new ResponseEntity<Set<Appliance>>(HttpStatus.FORBIDDEN);
        }

        User user = userDao.findById(id);

        if(user == null) {
            return new ResponseEntity<Set<Appliance>>(HttpStatus.NOT_FOUND);
        }

        Set<Appliance> applianceList = user.getApplianceList();

        if(applianceList.isEmpty()) {
            return new ResponseEntity<Set<Appliance>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<Appliance>>(applianceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/appliance/", method = RequestMethod.POST)
    public ResponseEntity<Appliance> addAppliances(@RequestBody Appliance appliance, UriComponentsBuilder ucBuilder) {

        applianceService.addAppliance(appliance);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/appliance/{id}").buildAndExpand(appliance.getId()).toUri());
        return new ResponseEntity<Appliance>(appliance, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/appliance/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Appliance> deleteAppliances(@PathVariable("id") Integer id) {

        Appliance appliance = applianceService.findById(id);

        if(appliance == null) {
            return new ResponseEntity<Appliance>(HttpStatus.NOT_FOUND);
        }

        applianceService.deleteAppliance(id);
        return new ResponseEntity<Appliance>(HttpStatus.NO_CONTENT);
    }
}

package com.COSC4P02.PanoTour.controllers;


import com.COSC4P02.PanoTour.entities.Panoview;
import com.COSC4P02.PanoTour.entities.PanoviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/panoview")
public class PanoviewController {
    private PanoviewDAO panoviewDAO;

    @Autowired
    public void setPanoview(@Qualifier("Panoview") PanoviewDAO panoviewDAO) {
        this.panoviewDAO = panoviewDAO;
    }

    @GetMapping(path= "fromSid")
    @PreAuthorize("hasAuthority('museum:read')")
    public Optional<Panoview> getPanoviewFromSid(@RequestParam(value = "sid") int sid) {
        return panoviewDAO.getPanoviewFromSid(sid);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('museum:write')")
    @ResponseBody
    public int addPanoview(@RequestBody Panoview panoview) {
        if (!panoviewDAO.addPanoview(panoview)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Panoview Could Not Be Added");
        }
        return panoview.getPid();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('museum:read')")
    public Optional<Panoview> getPanoview(@RequestParam(value = "pid") int pid) {
        return panoviewDAO.getPanoviewByPid(pid);
    }
}
package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.DTO.InvertDTO;
import com.Maruszak.MantisKeeper.services.InvertebratesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class InvertebrateController {

    @Autowired
    InvertebratesServiceImpl invertService;

    @GetMapping(path = "/myInverts")
    public String myInverts(Model model){
        return invertService.getMyAliveInvertsHTML(model);
    }

    @GetMapping(path = "/myInverts/addInvert")
    public String addNew(Model model){return invertService.addInvertHTML(model);}

    @GetMapping(path = "/myInverts/edit/{id}")
    public String editInvert(@PathVariable UUID id, Model model) {
        return invertService.editInvertHTML(id, model);
    }

    @GetMapping(path = "/myInverts/markDead/{id}")
    public String markInvertDead(@PathVariable UUID id, Model model) {
        return invertService.markInvertDeadHTML(id, model);
    }

    @GetMapping(path = "myInverts/dead")
    public String deadInverts(Model model){
        return invertService.deadInvertsHTML(model);
    }

    @GetMapping(path = "myInverts/details/{id}")
    public String invertDetails(@PathVariable UUID id, Model model){
        return invertService.invertDetailsHTML(id, model);
    }

    //--------api------

    @PostMapping(path = "/api/addInvert")
    public @ResponseBody
    void addInvert(@RequestPart(value = "invertDTO") InvertDTO invertDTO,
                   @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {
        invertService.saveNewInvert(invertDTO, photos);
    }

    @PostMapping(path = "/api/updateInvert")
    public @ResponseBody
    void updateInvert(@RequestPart("invertDTO") InvertDTO invertDTO,
                      @RequestPart(value = "photos", required = false) List<MultipartFile> photos,
                      @RequestPart (value = "removePhotos", required = false) List<UUID> toRemovePhotosIds) {
        invertService.updateInvert(invertDTO, photos, toRemovePhotosIds);
    }

    @DeleteMapping(path = "/api/deleteInvert/{id}")
    public @ResponseBody
    void deleteInvert(@PathVariable UUID id) {
        invertService.deleteInvert(id);
    }

    @PostMapping(path = "/api/saveAsDead/{id}")
    public @ResponseBody
    void markDead(@PathVariable UUID id, @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        invertService.saveAsDead(id, date);
    }
}

package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.InvertebrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InvertebratesServiceImpl{

    @Autowired
    private InvertebrateRepository invertRepository;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private InvertebratesServiceImpl invertService;

    public List<Invertebrate> getUserInvertebrates(User user){
        return invertRepository.findAllByUser(user);
    }

    public void addInvert(Invertebrate invertebrate) {
        invertRepository.save(invertebrate);
    }

    public Invertebrate findInvertById(long id){
        Optional<Invertebrate> invertOptional = invertRepository.findById(id);
        if(invertOptional.isPresent()){
            return invertOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Invertebrate not found!");
    }

    public Invertebrate saveInvert(Invertebrate invertebrate){
        return invertRepository.save(invertebrate);
    }

    public void addInvert(Instar instar) {
        instarService.addInstar(instar);
        Invertebrate invertebrate =  instar.getInvertebrate();
        invertService.addInvert(invertebrate);
    }

    public void updateInvert(List<Instar> instars) {
        instarService.deleteAllByInvert(instars.get(0).getInvertebrate());
        for( Instar instar : instars){
            instarService.saveInstar(instar);
        }
        invertService.saveInvert(instars.get(0).getInvertebrate());
    }

    public String editInvert(long id, Model model) {
        User user = userService.getUser();
        Invertebrate invert = invertService.findInvertById(id);
        model.addAttribute("invert", invert);
        model.addAttribute("user", user);
        return "editInvert";
    }
}

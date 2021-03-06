package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.ChangePasswordDTO;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.model.VerificationToken;
import com.Maruszak.MantisKeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvertebratesServiceImpl invertService;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private VerificationTokenServiceImpl tokenService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PhotoServiceImpl photoService;


    @Transactional
    public void register(User userTemp, HttpServletRequest request) {

        Optional<User> userOptional = userRepository.findByEmail(userTemp.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Email address already in use");
        }
        userOptional = userRepository.findByUsername(userTemp.getUsername());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        User user = new User();
        user.setUsername(userTemp.getUsername());
        user.setEmail(userTemp.getEmail());
        user.setPassword(passwordEncoder.encode(userTemp.getPassword()));
        user.setInvertebratesList(new ArrayList<>());
        user.setAuthority("USER");
        user.setActive(false);
        userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken(user);
        tokenService.save(verificationToken);

        String hostAddress = request.getServerName();
        SimpleMailMessage mail = createActivationEmail(verificationToken, user.getEmail(), hostAddress);

        emailService.sendEmail(mail);
    }

    public User getUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getClass() != User.class) {
            return null;
        }
        return (User) user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user;
        if(username.contains("@")){
            user = userRepository.findByEmail(username);
        }else {
            user = userRepository.findByUsername(username);
        }
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return user.get();
    }

    public String profileHTML(Model model, UUID id) {
        User user;
        UUID liveUserId;
        if (id!=null) {
            Optional<User> userOpt = userRepository.findById(id);
            if(userOpt.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "No user found");
            }
            user = userOpt.get();

        }else {
            user= getUser();

        }
        liveUserId = getUser().getId();

        user.setInvertebratesList(invertService.findInvertsByUser(user));
        model.addAttribute("id" ,liveUserId);
        model.addAttribute("user", user);
        return "userProfile";
    }

    @Transactional
    public void changePass(ChangePasswordDTO passwords) {
        User user = getUser();
        if (passwordEncoder.matches(passwords.getOldPass(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwords.getNewPass()));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Old Password!");
        }
    }

    @Transactional
    public String activateUserHTML(String token, Model model) {
        Optional<VerificationToken> tokenOptional = tokenService.findByToken(token);
        if (tokenOptional.isEmpty()) {
            model.addAttribute("message", "Bad link or token doesn't exist!");
        } else {
            VerificationToken verificationToken = tokenOptional.get();
            if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                model.addAttribute("message", "Token expired!");
            } else {
                User user = tokenOptional.get().getUser();
                if (user.isActive()) {
                    model.addAttribute("message", "User account already active");
                } else {
                    user.setActive(true);
                    userRepository.save(user);
                    tokenService.deleteTokenById(verificationToken.getId());
                    model.addAttribute("message", "User account successfully activated");
                }
            }
        }
        return "activation";
    }

    @Transactional
    public void resendToken(String email, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No account found with " + email + " address");
        }
        VerificationToken token = new VerificationToken(userOptional.get());
        tokenService.save(token);

        String hostAddress = request.getServerName();
        SimpleMailMessage mail = createActivationEmail(token, email, hostAddress);
        emailService.sendEmail(mail);

    }

    private SimpleMailMessage createActivationEmail(VerificationToken token, String email, String hostAddress) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Complete Registration for Inverts Keepers Website");
        mail.setFrom("invertebrates.keepers@gmail.com");
        mail.setText("To confirm your account, please click here: " +
                "https://" + hostAddress + "/confirmAccount?token=" + token.getToken() +
                "\n(active 24h)");
        return mail;
    }

    @Transactional
    public void resetPasswordRequest(String email, HttpServletRequest request) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No account found with " + email + " address");
        }
        if (!userOptional.get().isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User account not active!");
        }
        VerificationToken token = new VerificationToken(userOptional.get());
        tokenService.save(token);

        String hostAddress = request.getServerName();
        SimpleMailMessage mail = createResetPassEmail(token, email, hostAddress);
        emailService.sendEmail(mail);
    }

    private SimpleMailMessage createResetPassEmail(VerificationToken token, String email, String hostAddress) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Reset Your Inverts Keepers Account Password");
        mail.setFrom("invertebrates.keepers@gmail.com");
        mail.setText("To reset password, please click here: " +
                "https://" + hostAddress + "/resetPassword?token=" + token.getToken() +
                "\n(active 24h)");
        return mail;
    }

    public String resetPasswordHTML(String token, Model model) {
        Optional<VerificationToken> tokenOptional = tokenService.findByToken(token);
        if (tokenOptional.isEmpty()) {
            model.addAttribute("message", "Bad link or token doesn't exist!");
        } else {
            model.addAttribute("token", token);
        }
        return "resetPassword";
    }

    @Transactional
    public void resetPassword(ChangePasswordDTO changePasswordDTO) {
        Optional<VerificationToken> tokenOptional = tokenService.findByToken(changePasswordDTO.getToken());
        if (tokenOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad token!");
        }

        if(changePasswordDTO.getNewPass().length()<8){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password too short!");
        }

        User user = tokenOptional.get().getUser();

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPass()));
        userRepository.save(user);
        tokenService.deleteTokenById(tokenOptional.get().getId());
    }
    @Transactional
    public void deleteAccount(HttpServletRequest request, ChangePasswordDTO changePasswordDTO) {


        User user =getUser();
        if(!passwordEncoder.matches(changePasswordDTO.getOldPass(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Wrong old password!");
        }
        List<Invertebrate> inverts = invertService.findInvertsByUser(user);
        for(Invertebrate invert : inverts){
            instarService.deleteAllByInvert(invert);
            photoService.deleteAllByInvert(invert);
        }
        invertService.deleteAll(inverts);
        tokenService.deleteAllByUser(user);
        userRepository.delete(user);
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findUserByID(UUID id) {
        return userRepository.findById(id);
    }
}

package com.websocketserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserController {

    private final SimpUserRegistry simpUserRegistry;

    private final SimpMessagingTemplate template;

    @Autowired
    public UserController(SimpUserRegistry simpUserRegistry, SimpMessagingTemplate template) {
        this.simpUserRegistry = simpUserRegistry;
        this.template = template;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String get() {
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }*/

        Set<SimpSubscription> sub = simpUserRegistry
                .findSubscriptions(s -> s.getDestination().equals("/user/queue/1"));
        sub.forEach(s -> {
            System.out.println(s.getDestination());
            System.out.println(s.getId());
            System.out.println(s.getSession());
            String username = s.getSession().getUser().getName();
            template.convertAndSendToUser(username, "/queue/1", "hello " + username);
        });
        return "hello user";
    }
}

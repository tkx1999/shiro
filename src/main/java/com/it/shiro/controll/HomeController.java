package com.it.shiro.controll;

import com.it.shiro.pojo.User;
import com.it.shiro.service.UserService;
import com.it.shiro.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @GetMapping("login")
    public Object login(Model model) {
        model.addAttribute("login","Here is Login page");
        return "login";
    }

    @GetMapping("unauthc")
    public Object unauthc() {
        return "Here is Unauthc page";
    }

    @GetMapping("doLogin")
    public Object doLogin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "password error!";
        } catch (UnknownAccountException uae) {
            return "username error!";
        }

        User user = userService.findUserByName(username);
        subject.getSession().setAttribute("user", user);
        return "SUCCESS";
    }

    @GetMapping("register")
    public Object register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        passwordHelper.encryptPassword(user);

        userService.saveUser(user);
        return "SUCCESS";
    }
}
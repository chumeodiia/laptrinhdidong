/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.iotstar.restcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Trang home public
    @GetMapping("/")
    public String index() {
        return "index"; // => templates/index.html
    }

    // Trang sau khi login thành công
    @GetMapping("/home")
    public String home() {
        return "home";  // => templates/home.html
    }

    // Trang login custom
    @GetMapping("/login")
    public String login() {
        return "login"; // => templates/login.html
    }
}


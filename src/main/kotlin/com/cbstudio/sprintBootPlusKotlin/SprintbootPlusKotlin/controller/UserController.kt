package com.cbstudio.sprintBootPlusKotlin.SprintbootPlusKotlin.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserController {

    @RequestMapping("/MyFirstPage")
    fun greeting(@RequestParam(value = "title", required = false, defaultValue = "xiao")
                 title: String,
                 model: Model): String {
        model.addAttribute("name", title)
        return "index"
    }
}
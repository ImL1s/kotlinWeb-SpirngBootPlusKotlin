package com.cbstudio.sprintBootPlusKotlin.SprintbootPlusKotlin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays.asList

@RestController
class HelloController {

    @GetMapping(value = ["/hello", "/hello2"])
    fun hello(): Any {
        return "hello"
    }
}
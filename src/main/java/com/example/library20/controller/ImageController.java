package com.example.library20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.io.InputStream;


@Controller
public class ImageController {

    private final String IMG_PATH = "/templates/img/";

    @GetMapping(value = "/getImage/{imageName}")
    public @ResponseBody byte[] getImage(@PathVariable String imageName)  {
        InputStream in = getClass().getResourceAsStream(IMG_PATH + imageName);
        try {
            if (in != null) {
                return in.readAllBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

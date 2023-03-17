package com.example.library20.controller;

import com.example.library20.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/getImage/{imageName}")
    public @ResponseBody byte[] getImage(@PathVariable String imageName)  {
        return imageService.getImg(imageName);
    }

}

package org.zerock.memberboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.memberboard.dto.MovieDTO;
import org.zerock.memberboard.service.MovieService;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    //영화등록
    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes rttr){
        //MovieDTO : movie + imageList
        log.info("등록할 영화정보 : "+movieDTO);
        Long mno = movieService.register(movieDTO);
        rttr.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }
}

package org.zerock.memberboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.service.BoardService;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService serviceBoard;

    //보드 리스트
    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list 페이지 호출/받은 페이지 정보 : "+pageRequestDTO);
        model.addAttribute("result", serviceBoard.getList(pageRequestDTO));
    }

    //게시물 등록
    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("새로 추가된 엔티티 : " + dto);
        //새로 추가된 엔티티의 번호
        Long bno = serviceBoard.register(dto);
        log.info("BNO: " + bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    //게시물 조회+변경
    @GetMapping({"/read", "/modify" })
    public void read(@ModelAttribute ("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        log.info("조회할 bno: " + bno);
        BoardDTO boardDTO = serviceBoard.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    //게시물 변경
    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        serviceBoard.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";

    }

    //게시물 삭제
    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){

        log.info("삭제할 bno: " + bno);
        serviceBoard.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";

    }

}

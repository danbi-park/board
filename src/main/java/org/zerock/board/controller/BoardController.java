package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
     private final BoardService boardService;

    @GetMapping({"","/","/list"})
    public String boardList(PageRequestDTO pageRequestDTO, Model model){
/*        log.info("/board/list...");
        log.info(">>>>>>>>>>>>>>>>>" +pageRequestDTO);*/
        model.addAttribute("result", boardService.getList(pageRequestDTO));

        return "/board/list";
    }

    @GetMapping("/register")
    public void register(){
    }



    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("registerPost...");
        Long bno = boardService.register(dto);
        redirectAttributes.addFlashAttribute("msg", bno);
        redirectAttributes.addFlashAttribute("noti","등록");
        return "redirect:/board/list";
    }


/*    @GetMapping({"/read","/modify"})
    public void read(Long bno, Model model,
                     @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }*/



    @PostMapping("/modify")
    public String modify(BoardDTO dto, RedirectAttributes redirectAttributes,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO){
        log.info("post modify.............................");
        log.info("dto: " + dto);
        boardService.modify(dto); //save되서 덮어씀
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("Bno", dto.getBno()); //글번호도 넘겨줌, flash 안씀

        return "redirect:/board/read";
    }

/*    @PostMapping("/remove")                             //only one time
    public String remove(Long bno, RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO) {
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        redirectAttributes.addFlashAttribute("noti","삭제");

//      추가
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/board/list";
    }*/
}

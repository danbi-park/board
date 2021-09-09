package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTests {

    //테스트는 일부만 뽑아서 하기 때문에 Auto는 걔만 꼭집어서 스프링 빈을 만들어서 테스트 하는 것임
    @Autowired
    private BoardService boardService;

/*    @Test
    void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@ds.com")
                .build();
        //Long bno = boardService.register(dto);
        System.out.println(boardService.register(dto));
    }*/

/*    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }*/

/*    @Test
    public void testGet() {
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);


   @Test
    public void testRemove() {
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }
    }
*/


    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경222")
                .content("내용 변경222")
                .build();
        boardService.modify(boardDTO);
    }

}
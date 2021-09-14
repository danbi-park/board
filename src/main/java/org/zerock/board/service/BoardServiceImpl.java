package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository; //순환참조가 발생하기 때문에 final
    private final ReplyRepository replyRepository; //댓글 삭제 하기 위한 선언

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        //정렬하기 위해
        Function<Object[], BoardDTO> fn = (en -> entityToDTO(
                (Board) en[0], (Member) en[1], (Long) en[2]));
        //페이지 생성
//        Page<Object[]> result = repository.getBoardWithReplyCount(
//                pageRequestDTO.getPageable(Sort.by("bno").descending()));
          Page<Object[]> result = repository.searchPage(
                  pageRequestDTO.getType(),
                  pageRequestDTO.getKeyword(),
                  pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno){
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }


    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        repository.deleteById(bno);
        //위의 둘이 같이 일어나니까 Transactional어노테이션을 붙인 거임
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = repository.findById(boardDTO.getBno());
        if(result.isPresent()){
            Board board = result.get();

            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());

            repository.save(board);
        }
    }

/*  위와 같은 내용
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getById(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }*/


}

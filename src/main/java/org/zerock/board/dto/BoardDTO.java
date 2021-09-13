package org.zerock.board.dto;

import lombok.*;
import org.zerock.board.entity.Member;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; //작성자의 이메일(id)

    private String writerName;

    private LocalDateTime modDate;

    private LocalDateTime regDate;

    private int replyCount; //해당 게시글의 댓글 수
}

package com.project.doctor_fish_back.controller;

import com.project.doctor_fish_back.aspect.annotation.ValidAop;
import com.project.doctor_fish_back.dto.request.comment.ReqModifyCommentDto;
import com.project.doctor_fish_back.dto.request.comment.ReqRegisterCommentDto;
import com.project.doctor_fish_back.exception.AuthorityException;
import com.project.doctor_fish_back.service.CommentService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 작성
    @ValidAop
    @PostMapping("/review/comment")
    public ResponseEntity<?> writeComment(@Valid @RequestBody ReqRegisterCommentDto dto, BindingResult bindingResult) throws NotFoundException {
        return ResponseEntity.ok().body(commentService.writeComment(dto));
    }

    // 댓글 조회
    @GetMapping("/review/{reviewId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long reviewId) throws NotFoundException {
        return ResponseEntity.ok().body(commentService.getComments(reviewId));
    }

    // 댓글 수정
    @ValidAop
    @PutMapping("/review/comment")
    public ResponseEntity<?> modifyComment(@Valid @RequestBody ReqModifyCommentDto dto, BindingResult bindingResult) throws NotFoundException, AuthorityException {
        return ResponseEntity.ok().body(commentService.modifyComment(dto));
    }

    // 댓글 삭제
    @DeleteMapping("/review/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws NotFoundException, AuthorityException {
        return ResponseEntity.ok().body(commentService.deleteComment(commentId));
    }

}

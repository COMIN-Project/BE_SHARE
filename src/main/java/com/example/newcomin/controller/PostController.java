package com.example.newcomin.controller;

import com.example.newcomin.entity.Post;
import com.example.newcomin.entity.User;
import com.example.newcomin.service.PostService;
import com.example.newcomin.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;
    private UserService userService;

    // 등록
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        if (post != null && post.getUserId() != null
                && post.getPostTitle() != null && post.getPostContent() != null) {
            Long userId = post.getUserId().getUserId();
            User user = userService.getUserById(userId);

            if (user != null) {
                LocalDateTime postDate = LocalDateTime.now(); // 현재 시간을 가져와서 postDate로 설정
                Post savedPost = postService.createPost(user, postDate, post.getPostTitle(), post.getPostContent());
                return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
            } else {
                // Admin 생성에 실패한 경우 처리할 코드를 추가할 수 있습니다.
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") Long postId){
        Post post = postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    // 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") Long postId,
                                           @RequestBody Post post){
        post.setPostId(postId);
        Post updatedPost = postService.updatePost(post);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{postId}")
    public  ResponseEntity<String> deletePost(@PathVariable("postId") Long postId){
        postService.deletePost((postId));
        return new ResponseEntity<>("건의사항을 삭제했습니다.", HttpStatus.OK);
    }
}

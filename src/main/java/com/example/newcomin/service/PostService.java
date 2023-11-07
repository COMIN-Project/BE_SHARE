package com.example.newcomin.service;

import com.example.newcomin.entity.Post;
import com.example.newcomin.entity.User;

import java.time.LocalDateTime;
import java.util.List;


public interface PostService {
    Post createPost(User user, LocalDateTime postDate,
                    String postTitle, String postContent);

    Post getPostById(Long postId);

    List<Post> getAllPosts();

    Post updatePost(Post post);

    void deletePost(Long PostId);
}

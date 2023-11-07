package com.example.newcomin.service;

import com.example.newcomin.entity.Post;
import java.util.List;


public interface PostService {
    Post createPost(Post post);

    Post getPostById(Long postId);

    List<Post> getAllPosts();

    Post updatePost(Post post);

    void deletePost(Long PostId);
}

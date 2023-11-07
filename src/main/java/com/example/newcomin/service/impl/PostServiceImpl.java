package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Post;
import com.example.newcomin.repository.PostRepository;
import com.example.newcomin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.get();
    }
    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Post post){
        Post existingPost = postRepository.findById(post.getPostId()).get();
        existingPost.setPostTitle(post.getPostTitle());
        existingPost.setPostContent(post.getPostContent());
        existingPost.setPostDate(post.getPostDate());
        Post updatedPost = postRepository.save(existingPost);
        return  updatedPost;
    }

    @Override
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}

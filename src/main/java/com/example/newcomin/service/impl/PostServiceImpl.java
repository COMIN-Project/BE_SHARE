package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Post;
import com.example.newcomin.entity.User;
import com.example.newcomin.repository.PostRepository;
import com.example.newcomin.repository.UserRepository;
import com.example.newcomin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post createPost(User user, LocalDateTime postDate,
                           String postTitle, String postContent) {
        if (user != null && postTitle != null && postContent != null) {

            Post post = new Post();
            post.setUserId(user);
            post.setPostDate(postDate);
            post.setPostTitle(postTitle);
            post.setPostContent(postContent);

            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("유효하지 않은 건의사항 정보입니다.");
        }
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

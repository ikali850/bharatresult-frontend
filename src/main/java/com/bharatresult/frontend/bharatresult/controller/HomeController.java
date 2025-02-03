package com.bharatresult.frontend.bharatresult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.bharatresult.frontend.bharatresult.entity.Messages;
import com.bharatresult.frontend.bharatresult.entity.Post;
import com.bharatresult.frontend.bharatresult.service.PostService;

@Controller
public class HomeController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
    @GetMapping("/post/{url}")
    public ModelAndView getPost(@PathVariable("url") String url) {
        Post post = this.postService.getPost(url);
        ModelAndView mv=new ModelAndView("blog");
        mv.addObject("post", post);
        return mv;
    }
    

    @GetMapping("/result")
    public ModelAndView resultPage() {
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getPostsByCategory("result", 25, 0);
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Result");
        mv.addObject("pageUrl", "result");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }
    @GetMapping("/result/page/{pageNo}")
    public ModelAndView resultPages(@PathVariable("pageNo")int pageNo) {
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getPostsByCategory("result", 25, pageNo-1);
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Result");
        mv.addObject("pageUrl", "result");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/jobs")
    public ModelAndView jobPage() {
        ModelAndView mv = new ModelAndView("list");
        // List<BlogPost> resultList = this.blogRepository.findByCategory("job");
        // mv.addObject("listData", resultList);
        mv.addObject("title", "Latest Jobs");
        mv.addObject("value2", "Recent Exams");
        return mv;
    }

    @GetMapping("/contact-us")
    public ModelAndView contactUsPage() {
        return new ModelAndView("contact-us.html", HttpStatus.OK);
    }

    @GetMapping("/about-us")
    public ModelAndView aboutUsPage() {
        return new ModelAndView("about-us.html", HttpStatus.OK);
    }

    @GetMapping("/not-found")
    public ModelAndView errorPage() {
        return new ModelAndView("error.html", HttpStatus.OK);
    }

    @PostMapping("/message")
    public ModelAndView sendMessage(@ModelAttribute Messages message, RedirectAttributes redirectAttributes) {
        String apiurl = "http://localhost:5500/message";
        // Prepare the HTTP headers (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        // Wrap the user entity in HttpEntity
        HttpEntity<Messages> entity = new HttpEntity<>(message, headers);
        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(apiurl, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode().value() == 200) {
            redirectAttributes.addFlashAttribute("eventMsg", "Message sent..");
            return new ModelAndView(new RedirectView("/contact-us"));
        }

        redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong!");
        return new ModelAndView(new RedirectView("/contact-us"));
    }

}

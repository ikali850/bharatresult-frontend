package com.bharatresult.frontend.bharatresult.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.bharatresult.frontend.bharatresult.entity.Messages;
import com.bharatresult.frontend.bharatresult.entity.Post;
import com.bharatresult.frontend.bharatresult.entity.TrendingPost;
import com.bharatresult.frontend.bharatresult.repository.MessageUsRepo;
import com.bharatresult.frontend.bharatresult.service.PostService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private MessageUsRepo messageUsRepo;
    @Autowired
    private PostService postService;
    @Autowired
    private RedisTemplate redisTemplate;

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public ModelAndView homePage() {
        logger.info("HomePage Called");
        List<Post> jobsList = this.postService.getJobs(25, 0).getItems();
        List<Post> admissionList = this.postService.getAdmissions(25, 0).getItems();
        List<Post> admitCardList = this.postService.getExams(25, 0).getItems();
        List<Post> resultList = this.postService.getResults(25, 0).getItems();
        List<Post> syllabusList = this.postService.getSyllbus(25, 0).getItems();
        List<Post> otherFormsList = this.postService.getOtherForms(25, 0).getItems();
        List<TrendingPost> trendingPosts = this.postService.getTrendingPosts();
        ModelAndView mv = new ModelAndView("index");

        // Check if the list is large enough, otherwise take the whole list
        int jobListSize = Math.min(jobsList.size(), 14);
        int admitCardListSize = Math.min(admitCardList.size(), 14);
        int resultListSize = Math.min(resultList.size(), 14);
        int admissionListSize = Math.min(admissionList.size(), 10);
        int syllabusListSize = Math.min(syllabusList.size(), 10);
        int otherFormsListSize = Math.min(otherFormsList.size(), 10);
        mv.addObject("trendingPost", trendingPosts.toArray());
        mv.addObject("jobList", jobsList.subList(0, jobListSize));
        mv.addObject("admissionList", admissionList.subList(0, admissionListSize));
        mv.addObject("admitCardList", admitCardList.subList(0, admitCardListSize));
        mv.addObject("resultList", resultList.subList(0, resultListSize));
        mv.addObject("syllabusList", syllabusList.subList(0, syllabusListSize));
        mv.addObject("otherFormsList", otherFormsList.subList(0, otherFormsListSize));

        return mv;
    }

    @GetMapping("/post/{url}")
    public ModelAndView getPost(@PathVariable("url") String url) {
        logger.info("GET Post Called with URL :" + url);
        Post post = this.postService.getPost(url);
        ModelAndView mv = new ModelAndView("blog");
        mv.addObject("post", post);
        return mv;
    }

    @GetMapping("/result")
    public ModelAndView resultPage() {
        logger.info("result page  called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getResults(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Result");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/result/page/{pageNo}")
    public ModelAndView resultPages(@PathVariable("pageNo") int pageNo) {
        logger.info("result page   called with pageNo :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getResults(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Result");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/syllabus")
    public ModelAndView syllabusPage() {
        logger.info("syllabus page  called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getSyllbus(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Syllabus");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/syllabus/page/{pageNo}")
    public ModelAndView syllabusPage2(@PathVariable("pageNo") int pageNo) {
        logger.info("syllabus page   called with pageNO :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getSyllbus(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Syllabus");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/job")
    public ModelAndView jobsPage() {
        logger.info("job page  called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getJobs(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Job");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/job/page/{pageNo}")
    public ModelAndView jobsPage2(@PathVariable("pageNo") int pageNo) {
        logger.info("job page called with id :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getJobs(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Job");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/admit-card")
    public ModelAndView examPage() {
        logger.info("admit card page Called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getExams(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Admit-Card");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/admit-card/page/{pageNo}")
    public ModelAndView examPage2(@PathVariable("pageNo") int pageNo) {
        logger.info("admit-card  page called with pageNo   :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getJobs(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Admit-Card");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/other-form")
    public ModelAndView otherformPage() {
        logger.info("other-form page  called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getOtherForms(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Other-form");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/other-form/page/{pageNo}")
    public ModelAndView otherFormPage2(@PathVariable("pageNo") int pageNo) {
        logger.info("other-form  page called with pageNo :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getOtherForms(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "Other-form");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/admission")
    public ModelAndView admissionPage() {
        logger.info("addmission page called!");
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getAdmissions(25, 0).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "admission");
        mv.addObject("currentPage", 1);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/admissiion/page/{pageNo}")
    public ModelAndView admissionPage2(@PathVariable("pageNo") int pageNo) {
        logger.info("addmission page called with pageNumber :" + pageNo);
        ModelAndView mv = new ModelAndView("list");
        Page<Post> postPagesList = this.postService.getAdmissions(25, pageNo - 1).getPage();
        List<Post> postList = postPagesList.getContent();
        mv.addObject("pageTitle", "admission");
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", postPagesList.getTotalPages());
        mv.addObject("postList", postList);
        return mv;
    }

    @GetMapping("/contact-us")
    public ModelAndView contactUsPage() {
        logger.info("contact  us page called!");
        return new ModelAndView("contact-us.html", HttpStatus.OK);
    }

    @GetMapping("/about-us")
    public ModelAndView aboutUsPage() {
        logger.info("about us page called!");
        return new ModelAndView("about-us.html", HttpStatus.OK);
    }

    @GetMapping("/service")
    public ModelAndView servicePage() {
        logger.info("service page called!");
        return new ModelAndView("service", HttpStatus.OK);
    }

    @GetMapping("/tools")
    public ModelAndView toolsPage() {
        logger.info("tools page called! ");
        return new ModelAndView("tools", HttpStatus.OK);
    }

    @GetMapping("/not-found")
    public ModelAndView errorPage() {
        logger.error("error page called!");
        return new ModelAndView("error.html", HttpStatus.OK);
    }

    @PostMapping("/message")
    public ModelAndView sendMessage(@ModelAttribute Messages message, RedirectAttributes redirectAttributes) {
        logger.info("send message called with data: " + message);
        Messages savedMessages = this.messageUsRepo.save(message);
        if (savedMessages != null) {
            redirectAttributes.addFlashAttribute("eventMsg", "Message sent..");
            return new ModelAndView(new RedirectView("/contact-us"));
        }
        redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong!");
        return new ModelAndView(new RedirectView("/contact-us"));
    }

    @GetMapping("/clear-cache")
    public ModelAndView clearCache() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        return new ModelAndView(new RedirectView("/"));
    }

}

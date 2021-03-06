package cn.liangjiateng.controller.views.back;

import cn.liangjiateng.common.ServiceException;
import cn.liangjiateng.pojo.DO.Article;
import cn.liangjiateng.pojo.DO.Job;
import cn.liangjiateng.pojo.VO.JobVO;
import cn.liangjiateng.service.ArticleService;
import cn.liangjiateng.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jiateng on 7/1/18.
 */
@Controller
@RequestMapping(value = "/back/cronjobs")
public class BackCronJobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String cronJobs(ModelMap modelMap) {
        //草稿数量
        long cnt = articleService.countArticleByStatus(Article.Status.OFFLINE);
        modelMap.addAttribute("draft_cnt", cnt);
        return "back_cronjob";
    }

    @GetMapping("/new")
    public String newJob(ModelMap modelMap) {
        //草稿数量
        long cnt = articleService.countArticleByStatus(Article.Status.OFFLINE);
        modelMap.addAttribute("draft_cnt", cnt);
        return "back_new_cronjob";
    }

    @GetMapping("/config/{jobId}")
    public String editJob(ModelMap modelMap, @PathVariable String jobId) throws ServiceException {
        Job job = jobService.getJobByJobId(jobId);
        JobVO jobVO = new JobVO(job);
        modelMap.addAttribute("job", jobVO);
        //草稿数量
        long cnt = articleService.countArticleByStatus(Article.Status.OFFLINE);
        modelMap.addAttribute("draft_cnt", cnt);
        modelMap.addAttribute("jobName", job.getName());
        return "back_edit_cronjob";
    }

}

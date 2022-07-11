package com.onlineExamApp.app.controller;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlineExamApp.app.model.Exam;
import com.onlineExamApp.app.model.ExamQuestions;
import com.onlineExamApp.app.model.ExamResults;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.model.SearchTeacher;
import com.onlineExamApp.app.model.Users;
import com.onlineExamApp.app.service.CalculationService;
import com.onlineExamApp.app.service.ExamQuestionsService;
import com.onlineExamApp.app.service.ExamResultsService;
import com.onlineExamApp.app.service.ExamService;
import com.onlineExamApp.app.service.ExamStudentsStatusService;
import com.onlineExamApp.app.service.ExamTeachersStatusService;
import com.onlineExamApp.app.service.MyUserDetails;
import com.onlineExamApp.app.service.UserDetailsServiceImpl;
import com.onlineExamApp.app.util.PaginatorHelper;

@Controller
@RequestMapping("/exam")
public class ExamController {
	
	@Autowired
	private ExamService service;
	
	@Autowired
	private ExamQuestionsService qservice;
	
	@Autowired
	private CalculationService cservice;
	
	@Autowired
	private UserDetailsServiceImpl uservice;
	
	@Autowired 
	private ExamTeachersStatusService extservice;
	
	@Autowired 
	private ExamStudentsStatusService exsservice;
	
	@Autowired
	private ExamResultsService rservice;
	
	private Integer flag;

	@RequestMapping(value= "", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@AuthenticationPrincipal MyUserDetails user,@ModelAttribute(value = "search") Search search,SearchTeacher searchName,SearchTeacher searchTeacher,Model model, 
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		
		Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        
        List<Exam> exams;
        Page<Exam> page;
        if (myRole.equals("TEACHER")) {
    		exams = service.listTeacherSearched(searchName,searchTeacher);
        }else {       	
        	exams = service.listPublishedSearched(search);
        	flag=0;
        	
       }
        
        page=PaginatorHelper.pagiableList(exams, pageable);
		model.addAttribute("user",user);
        model.addAttribute("exams", exams);
		model.addAttribute("page", page);
		model.addAttribute("cservice", cservice);
		model.addAttribute("extservice",extservice);
		model.addAttribute("exsservice",exsservice);
		
		
        return "exam/list";	
	}
	
	@RequestMapping("/add")
	public String add(@AuthenticationPrincipal MyUserDetails user,@ModelAttribute(value = "search") Search search, Model model,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		Exam exam = new Exam();
		ExamQuestions questions = new ExamQuestions();
		int id = service.getLastIdAndNewId();
		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		model.addAttribute("question", question);
		model.addAttribute("page", page);
		
		model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("questions", questions);
	    model.addAttribute("service",service);
	    model.addAttribute("qservice",qservice);
	    
		return "exam/add";
	}
	
	@RequestMapping(value = "/qsave", method = RequestMethod.POST)
	public String saveQuestion(@ModelAttribute("question") ExamQuestions question) {
		qservice.save(question);
		
		
		return "redirect:/exam/add";
	}
	
	@RequestMapping(value = "/editqsave", method = RequestMethod.POST)
	public String saveEditQuestion(@ModelAttribute("question") ExamQuestions question) {
		Integer exId = question.getExamId();
	    String eid = Integer.toString(exId);
		
		qservice.save(question);
		
		return  "redirect:/exam/edit/"+eid;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params="save")
	public String save(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	    
	    extservice.addSave(uservice.getIdByName(exam.getAddedBy()), exam.getId());
	     
	    return "redirect:/exam";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params="publish")
	public String publish(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	    
	    extservice.addPublish(uservice.getIdByName(exam.getAddedBy()), exam.getId());
	     
	    return "redirect:/exam";
	}
	
	@RequestMapping("/edit/{id}")
	public String showEditExamPage(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {

		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		Exam exam = service.get(id);
		ExamQuestions questions = new ExamQuestions();
		
		
		model.addAttribute("question", question);
		model.addAttribute("page", page); 
	    

	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("questions", questions);
	    model.addAttribute("qservice",qservice);
	    
		
	    return "exam/edit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params="editsave")
	public String editsave(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	    
	    extservice.editSave(exam.getId());
	     
	    return "redirect:/exam";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params="editpublish")
	public String editpublish(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	    
	    extservice.editPublish(exam.getId());
	     
	    return "redirect:/exam";
	}
	

	@RequestMapping("/enroll/{id}")
	public String enrollExam(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id,
			@PageableDefault(value = PaginatorHelper.ONE_PAGINATION_SIZE, page = 0) Pageable pageable) {
		ExamResults result = new ExamResults();	
		Exam exam = service.get(id);
		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		
		for(int c=0;c<question.size();c++) {
			
			ExamQuestions ex = question.get(c);
			Integer qid = ex.getQueId();
			
			ExamResults res = rservice.getResult(user.getId(), id, qid);
			
			if(res==null) {
				rservice.insertResult(user.getId(),id,qid);
			}
		}
		
		
		
		exsservice.insertStatus(user.getId(),id);
		
		Integer lqn = exsservice.getLastQueNo(user.getId(), exam.getId());
		
		if(flag==0) {
			pageable = PageRequest.of(lqn, PaginatorHelper.ONE_PAGINATION_SIZE);
			flag=1;
		}
		
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		
		model.addAttribute("question", question);
		model.addAttribute("page", page);
		
	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("exsservice",exsservice);
	    model.addAttribute("result",result);
	    model.addAttribute("rservice",rservice);
	    
		
	    return "exam/enroll";
	}
	
	
	@RequestMapping(value = "/resultsave/{pno}", method = RequestMethod.POST, params="prevUpdate")
	public String updatePrevGivenAnswer(@ModelAttribute("result") ExamResults result, @PathVariable(name = "pno") int pno) {
		exsservice.updateLastQueNo(result.getUserId(),result.getExamId(),pno-1);
		rservice.updateResult(result.getUserId(), result.getExamId(), result.getQueId(), result.getGivenAnswer());
		Integer lqn = exsservice.getLastQueNo(result.getUserId(), result.getExamId());
		String lastqn = lqn.toString();
		String exId = result.getExamId().toString();
		
	    return "redirect:/exam/enroll/"+exId+"?page="+lastqn;
	}
	
	@RequestMapping(value = "/resultsave/{pno}", method = RequestMethod.POST, params="nextUpdate")
	public String updateNextGivenAnswer(@ModelAttribute("result") ExamResults result, @PathVariable(name = "pno") int pno) {
		exsservice.updateLastQueNo(result.getUserId(),result.getExamId(),pno+1);
		rservice.updateResult(result.getUserId(), result.getExamId(), result.getQueId(), result.getGivenAnswer());
		Integer lqn = exsservice.getLastQueNo(result.getUserId(), result.getExamId());
		String lastqn = lqn.toString();
		String exId = result.getExamId().toString();
		
	    return "redirect:/exam/enroll/"+exId+"?page="+lastqn;
	}
	
	@RequestMapping(value = "/resultsave/{pno}", method = RequestMethod.POST, params="saveUpdate")
	public String updateSaveGivenAnswer(@ModelAttribute("result") ExamResults result, @PathVariable(name = "pno") int pno) {
		rservice.updateResult(result.getUserId(), result.getExamId(), result.getQueId(), result.getGivenAnswer());
		
	    return "redirect:/exam";
	}
	
	
	
	@RequestMapping(value = "/resultsave/{pno}", method = RequestMethod.POST, params="completeUpdate")
	public String updateCompleteStudentExamResult(@ModelAttribute("result") ExamResults result, @PathVariable(name = "pno") int pno,
			@AuthenticationPrincipal MyUserDetails user,Model model,@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {

		exsservice.updateLastQueNo(result.getUserId(),result.getExamId(),pno);
		rservice.updateResult(result.getUserId(), result.getExamId(), result.getQueId(), result.getGivenAnswer());
		
		exsservice.updateStudentStatus(result.getUserId(), result.getExamId());
		
		List<ExamQuestions> question = qservice.listQuestionsAll(result.getExamId());
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		
		Exam exam = service.get(result.getExamId());
		
		model.addAttribute("question", question);
		model.addAttribute("page",page);
	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("rservice",rservice);
	    
		
	    return "exam/result";
	}
	
	
	@RequestMapping("/result/{id}/{uid}")
	public String resultExam(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id, @PathVariable(name = "uid") int uid,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {

		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		
		Exam exam = service.get(id);
		
		model.addAttribute("question", question);
		model.addAttribute("page",page);
	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("rservice",rservice);
	    
		
	    return "exam/result";
	}
	
	@RequestMapping("/resultTeacher/{id}/{sid}")
	public String resultTeacherExam(Model model, @PathVariable(name = "id") int id, @PathVariable(name = "sid") int sid,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		Users user = new Users();
		user.setId(sid);
		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		
		Exam exam = service.get(id);
		
		model.addAttribute("question", question);
		model.addAttribute("page",page);
	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("rservice",rservice);
	    
		
	    return "exam/result";
	}
	
	
	@RequestMapping("/resultStudent/{id}/{uid}")
	public String resultStudentExam(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id, @PathVariable(name = "uid") int uid,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {

		exsservice.updateStudentStatus(uid, id);
		
		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		
		Exam exam = service.get(id);
		
		model.addAttribute("question", question);
		model.addAttribute("page",page);
	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("rservice",rservice);
	    
		
	    return "exam/result";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(Model model,@PathVariable(name = "id") int id) {
	    service.delete(id);
	    extservice.deleteByExamId(id);
	    qservice.deleteByExamId(id);
	    exsservice.deleteByExamId(id);
	    rservice.deleteByExamId(id);
	    
	    return "redirect:/exam";
	}
	
	@RequestMapping("/questions/adddelete/{id}")
	public String deleteAddQuestion(Model model,@PathVariable(name = "id") int id) {
		qservice.delete(id);
	    
	    return "redirect:/exam/add/";
	}
	
	@RequestMapping("/questions/editdelete/{id}")
	public String deleteEditQuestion(Model model,@PathVariable(name = "id") int id) {
	    Integer exId = qservice.get(id).getExamId();
	    String eid = Integer.toString(exId);
		qservice.delete(id);
	    
	    return "redirect:/exam/edit/"+eid;
	}
	
	@RequestMapping("/status/{id}")
	public String showExamStatus(Model model,@PathVariable(name = "id") int id,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		Exam exam = service.get(id);
		
		List<Users> students = uservice.listStudents();
		Page<Users> page=PaginatorHelper.pagiableList(students, pageable);
		
		model.addAttribute("exam", exam);
		model.addAttribute("students",students);
		model.addAttribute("page", page);
		model.addAttribute("extservice",extservice);
		model.addAttribute("uservice",uservice);
		model.addAttribute("rservice",rservice);
		
		
		
		return "exam/status";
	}
	
	@RequestMapping("/end/{id}")
	public String endExam(Model model,@PathVariable(name = "id") int id) {
		
		extservice.finishExam(id);
		
		return "redirect:/exam";
	}
	
	@RequestMapping("/Tend/{id}")
	public String endTExam(Model model,@PathVariable(name = "id") int id) {
		
		extservice.finishExam(id);
		
		return "redirect:/exam/status/"+id;
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}

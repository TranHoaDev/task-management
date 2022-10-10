package com.project.management.controller;

import com.project.management.entity.TaskStatus;
import com.project.management.exporter.ExcelExport;
import com.project.management.exporter.PdfExport;
import com.project.management.repository.TaskRepository;
import com.project.management.repository.TaskStatusRepository;
import com.project.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import com.project.management.entity.Task;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    TaskService taskService;

    @RequestMapping({"/", "/index"})
    public String home() {
        return "redirect:/task/list";
    }

    @RequestMapping("/task/list")
    public ModelAndView list(ModelMap model) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Task> list = taskRepository.findAll(pageable);

        model.addAttribute("tasks", list);
        List<TaskStatus> listC = taskStatusRepository.findAll();
        model.addAttribute("taskStatus", listC);

        return new ModelAndView("admin/list", model);
    }

    @GetMapping("/task/page")
    public ModelAndView page(ModelMap model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam(value = "title", required = false) String title,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("brand") Optional<Long> brandPage) {

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);

        if (title.equalsIgnoreCase("null")) {
            title = "";
        }

        Long brand = brandPage.orElse(0L);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Task> list = null;

        if (brand == 0) {
            list = taskRepository.findByTitleContaining(title, pageable);
        } else {
            list = taskRepository.findAllTaskByTaskstatusId(brand, pageable);
        }

        model.addAttribute("brand", brand);
        model.addAttribute("tasks", list);
        model.addAttribute("title", title);

        List<TaskStatus> listC = taskStatusRepository.findAll();
        model.addAttribute("taskStatus", listC);

        return new ModelAndView("admin/list", model);
    }

    @RequestMapping("/task/search")
    public ModelAndView search(ModelMap model, @RequestParam("title") String title,
                               @RequestParam("size") Optional<Integer> size) {
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(0, pageSize);

        Page<Task> list = taskRepository.findByTitleContaining(title, pageable);

        model.addAttribute("title", title);
        model.addAttribute("tasks", list);

        List<TaskStatus> listC = taskStatusRepository.findAll();
        model.addAttribute("taskStatus", listC);

        return new ModelAndView("admin/list", model);
    }

    @GetMapping("/task/delete/{task_id}")
    public ModelAndView delete(@PathVariable("task_id") Long task_id, ModelMap model) {

        Optional<Task> opt = taskRepository.findById(task_id);
        if (opt.isPresent()) {
            taskRepository.delete(opt.get());
            model.addAttribute("message", "Xoá thành công!");
        } else {
            model.addAttribute("error", "Không thể xóa task này!");
        }
        return new ModelAndView("forward:/task/list", model);
    }

    @GetMapping("/task/viewtask/{task_id}")
    public String viewProduct(@PathVariable long task_id, Model model) {

        model.addAttribute("task", taskRepository.findById(task_id).get());

        return "admin/viewTask";
    }

    @GetMapping("/task/add")
    public String add(ModelMap model, Task task) {
        task.setTaskStatus(new TaskStatus());
        model.addAttribute("task", task);

        return "admin/addTask";
    }

    @PostMapping("/task/saveOrUpdate")
    public String saveProduct(ModelMap model, @Valid @ModelAttribute("task") Task task, BindingResult result) {

        model.addAttribute("task", task);
        if (result.hasErrors()) {
            return "admin/addTask";
        }
        taskRepository.save(task);
        return "redirect:/task/add";
    }

    @GetMapping("/task/edit/{task_id}")
    public ModelAndView edit(@PathVariable("task_id") Long task_id, ModelMap model) {

        Optional<Task> opt = taskRepository.findById(task_id);
        if (opt.isPresent()) {
            model.addAttribute("task", opt.get());
            model.addAttribute("message", "Cập nhật thành công!");
        } else {
            return list(model);
        }
        return new ModelAndView("admin/editTask", model);
    }

    @ModelAttribute("TaskStatus")
    public List<TaskStatus> getTaskStatus() {
        return taskService.findAllTasks();
    }

    @GetMapping("/task/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=task_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Task> listTask = taskRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Task ID", "Title", " Description"};
        String[] nameMapping = {"task_id", "title", "description"};

        csvWriter.writeHeader(csvHeader);

        for (Task task : listTask) {
            csvWriter.write(task, nameMapping);
        }
        csvWriter.close();
    }

    @GetMapping("/task/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=task_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Task> listTasks = taskRepository.findAll();

        PdfExport exporter = new PdfExport(listTasks);
        exporter.export(response);
    }

    @GetMapping("/task/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tasks_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Task> listTask = taskRepository.findAll();
        ExcelExport excelExport = new ExcelExport(listTask);
        excelExport.export(response);
    }
}

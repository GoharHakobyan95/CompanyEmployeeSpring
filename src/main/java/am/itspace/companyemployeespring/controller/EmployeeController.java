package am.itspace.companyemployeespring.controller;


import am.itspace.companyemployeespring.entity.Company;
import am.itspace.companyemployeespring.entity.Employee;
import am.itspace.companyemployeespring.repository.CompanyRepository;
import am.itspace.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class EmployeeController {
    @Value("${company.employee.images.folder}")
    private String folderPath;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/employees")
    public String allEmployees(ModelMap modelMap) {
        List<Employee> employees = employeeRepository.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setPicUrl(fileName);
        }
        Company company  = employee.getCompany();
        company.setSize(company.getSize() + 1);
        employeeRepository.save(employee);//tuyn es chgitey vor springy eeeedqan xelaci e, company name-ov haskanum e vor pti dra obyekty sarqi..hmmm shalt lav e apreq nor infoyi hamar))
        return "redirect:/employees";
    }

    @GetMapping("/employees/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}

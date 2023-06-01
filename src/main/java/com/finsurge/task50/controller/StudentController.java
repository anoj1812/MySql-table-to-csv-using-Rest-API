package com.finsurge.task50.controller;

import com.finsurge.task50.entity.Student;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.finsurge.task50.service.StudentServiceClass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@RestController
public class StudentController {
    @Autowired private StudentServiceClass studentServiceClass;

    // Save operation
    @PostMapping("/students")
    public Student saveStudent(
            @RequestBody Student student)
    {
        return studentServiceClass.saveStudent(student);
    }

    // Read operation
    @GetMapping("/students")
    public List<Student> fetchStudentList()
    {
        return studentServiceClass.fetchStudentList();
    }

    // Update operation
    @PutMapping("/students/{id}")
    public Student
    updateStudent(@RequestBody Student student,
                  @PathVariable("id") Long studentId)
    {
        return studentServiceClass.updateStudent(
                student, studentId);
    }

    // Delete operation
    @DeleteMapping("/students/{id}")
    public String deleteStudentById(@PathVariable("id")
                                    Long studentId)
    {
        studentServiceClass.deleteStudentById(
                studentId);
        return "Deleted Successfully";
    }
    @GetMapping("/students/export/csv")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("text/xlsx");
        DateFormat dateFormatter = new SimpleDateFormat("DDMMYYYY");
        String currentDate = dateFormatter.format(new Date());
        Date date = new Date();
        String strDateFormat = "HHMMSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time=sdf.format(date);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=students_" + currentDate+"_"+time;
        response.setHeader(headerKey, headerValue);

        List<Student> listStudents = studentServiceClass.fetchStudentList();



        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Student Id", "Student Name", "Student Dept", "Student Clg"};
        String[] nameMapping = {"studentId", "studentName", "studentDept", "studentClg"};

        csvWriter.writeHeader(csvHeader);

        for (Student student : listStudents) {
            csvWriter.write(student, nameMapping);
        }

        csvWriter.close();

    }
}



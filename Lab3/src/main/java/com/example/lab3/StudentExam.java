package com.example.lab3;

public class StudentExam {
    private int id;
    private int studentId;
    private int examId;
    private Student student;
    private Exam exam;

    public StudentExam(int studentId, int examId) {
        this.setStudentId(studentId);
        this.setExamId(examId);
    }

    public StudentExam() {
    }

    public StudentExam(Student student, Exam exam) {
        this.setStudent(student);
        this.setExam(exam);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}

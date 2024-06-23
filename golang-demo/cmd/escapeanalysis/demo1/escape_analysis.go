package main

import "fmt"

type Student struct {
	Name string
	Age  int
}

func NewStudent(name string, age int) Student {
	return Student{
		Name: name,
		Age:  age,
	}
}

func main() {
	stu := NewStudent("Crayon", 23)
	fmt.Println(stu)
}

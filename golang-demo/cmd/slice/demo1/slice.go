package main

import (
	"fmt"
	"reflect"
)

func main() {
	s1 := []int{1}
	s2 := []int{1, 2}
	fmt.Println(reflect.TypeOf(s1) == reflect.TypeOf(s2))

	s3 := make([]int, 2, 4)
	fmt.Printf("%v, %p\n", s3, s3)

	s3 = append(s3, 1)
	fmt.Printf("%v, %p\n", s3, s3)

	s3 = append(s3, 2)
	fmt.Printf("%v, %p\n", s3, s3)

	s3 = append(s3, 3)
	fmt.Printf("%v, %p\n", s3, s3)
}

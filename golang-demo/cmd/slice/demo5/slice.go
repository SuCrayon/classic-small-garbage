package main

import "fmt"

/*
切片扩容
*/

func main() {
	s1 := []int{1, 2}
	fmt.Printf("[before] s1: %v, len(s1): %d, cap(s1): %d\n", s1, len(s1), cap(s1))
	s1 = append(s1, 3, 4, 5)
	fmt.Printf("[after] s1: %v, len(s1): %d, cap(s1): %d\n", s1, len(s1), cap(s1))
}

package main

import "fmt"

func main() {
	arr := [5]int{0, 1, 2, 3, 4}
	s1 := arr[2:4]
	fmt.Printf("s1: %v, len(s1): %d, cap(s1): %d\n", s1, len(s1), cap(s1))
}

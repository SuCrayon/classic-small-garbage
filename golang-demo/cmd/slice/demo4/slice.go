package main

import "fmt"

func main() {
	arr := [5]int{0, 1, 2, 3, 4}
	s1 := arr[2:4:5]
	// 6大于数组的索引最大值，这里编译期就会报错
	// s2 := arr[2:4:6]
	fmt.Printf("s1: %v, len(s1): %d, cap(s1): %d\n", s1, len(s1), cap(s1))

	s1 = make([]int, 0, 10)
	s1 = append(s1, 0, 1, 2, 3, 4)
	// s1底层数组长度，即cap为10，所以8没有问题
	s2 := s1[2:4:8]
	fmt.Printf("s2: %v, len(s2): %d, cap(s2): %d\n", s2, len(s2), cap(s2))
}

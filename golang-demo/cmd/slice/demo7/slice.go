package main

import (
	"fmt"
)

/*
slice作为函数参数传递
*/

func main() {
	s := []int{1}
	// &s: 切片本身地址（指向切片这个结构体的指针）
	// s: 切片指向的底层数组地址
	// &s[0]: 切片指向的底层数组地址（数组是一片连续的地址空间，数组地址即为起始元素的地址）
	fmt.Printf("[function main] &s: %p, s: %p, &s[0]: %p\n", &s, s, &s[0])
	f(s)
	fmt.Println(s)
}

func f(s []int) {
	fmt.Printf("[function f, before growing] &s: %p, s: %p, &s[0]: %p\n", &s, s, &s[0])
	s = append(s, 2, 3, 4)
	fmt.Printf("[function f, after growing] &s: %p, s: %p, &s[0]: %p\n", &s, s, &s[0])
}

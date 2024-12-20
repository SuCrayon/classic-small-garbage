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
	fmt.Printf("[function main] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])
	f(s)
	fmt.Printf("[function main] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])

	fmt.Println()

	// 有多余空间的切片，验证不扩容情况下是否修改生效
	s = make([]int, 1, 4)
	s[0] = 1
	fmt.Printf("[function main] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])
	// 实际上没有扩容发生，所以函数内部打印地址的时候是不变的
	f(s)
	// 函数内部对于append操作成功修改底层的数组，外部是可以正常感知到的，修改生效
	fmt.Printf("[function main] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])
}

func f(s []int) {
	// 可以发现s的指针（地址）变化了，实际传递进来的是拷贝的新值（形参与实参的区别）
	fmt.Printf("[function f, before growing] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])
	s = append(s, 2, 3, 4)
	fmt.Printf("[function f, after growing] s: %v, &s: %p, &(s->arr): %p, &s[0]: %p\n", s[0:cap(s)], &s, s, &s[0])
}

package main

import (
	"fmt"
)

/*
map作为函数参数传递
*/

func main() {
	m := make(map[int]int, 0)
	// &m: 切片本身地址（指向切片这个结构体的指针）
	fmt.Printf("[function main] &m: %p, m: %p\n", &m, m)
	f(m)
	fmt.Println(m)
}

func f(m map[int]int) {
	fmt.Printf("[function f, before growing] &m: %p, m: %p\n", &m, m)

	for i := 0; i < 4; i++ {
		m[i+1] = i + 1
	}

	fmt.Printf("[function f, after growing] &m: %p, m: %p\n", &m, m)
}

package main

import "math/rand"

func Slice() {
	s := make([]int, 0, 10000)

	for i, _ := range s {
		s[i] = i
	}

	n := rand.Intn(10)
	_ = make([]int, n)

	_ = make(map[string]string, n)

	_ = make(chan string)
	_ = make(chan string, 1)
	_ = make(chan string, 10)
	_ = make(chan string, n)
}

func main() {
	Slice()
}

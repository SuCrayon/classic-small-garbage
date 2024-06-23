package main

import "fmt"

func gen() int {
	i := 0

	defer func() {
		i++
	}()

	return i
}

func main() {
	ret := gen()
	fmt.Println(ret)
}

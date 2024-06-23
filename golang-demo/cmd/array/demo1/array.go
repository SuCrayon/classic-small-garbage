package main

import (
	"fmt"
	"os"
	"runtime/pprof"
)

func main() {
	arr1 := [3]int{1, 2, 3}
	arr2 := [...]int{1, 2, 3}
	fmt.Println(arr1, arr2)
	fmt.Println(arr1 == arr2)

	f, err := os.OpenFile("heap.pprof", os.O_CREATE|os.O_RDWR, 0755)
	if err != nil {
		panic(err)
	}
	err = pprof.WriteHeapProfile(f)
	if err != nil {
		panic(err)
	}
}

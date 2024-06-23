package main

func foo() *int {
	t := 3
	return &t
}

func main() {
	_ = foo()
}

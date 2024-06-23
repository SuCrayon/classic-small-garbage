package main

import (
	"fmt"
	"unsafe"
)

type Type interface {
	Kind()
}

type Struct struct {
}

func (s *Struct) Kind() {

}

func ToInterface(v interface{}) Type {
	p := *(*Type)(unsafe.Pointer(&v))
	return p
}

func main() {
	s := Struct{}
	ToInterface(s)
	ToInterface(s).Kind()
	ToInterface(s).(*Struct).Kind()
	fmt.Println()
}

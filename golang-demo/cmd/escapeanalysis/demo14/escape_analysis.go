package main

import "unsafe"

type MyInterface interface {
	Do()
}

type Pointer struct {
}

func (s *Pointer) Do() {

}

func Pointer2MyInterface(s *Pointer) MyInterface {
	return s
}

type emptyInterface struct {
	typ  *Pointer
	word unsafe.Pointer
}

func TypeOf(i interface{}) MyInterface {
	eface := (*emptyInterface)(unsafe.Pointer(&i))
	return Pointer2MyInterface(eface.typ)
}

func TypeOf0(i interface{}) *Pointer {
	eface := (*emptyInterface)(unsafe.Pointer(&i))
	return eface.typ
}

func TypeOf1(i interface{}) Pointer {
	eface := (*emptyInterface)(unsafe.Pointer(&i))
	return *eface.typ
}

func main() {
	p1 := Pointer{}
	TypeOf(p1)
	TypeOf(p1).(*Pointer).Do()
	TypeOf(p1).Do()

	TypeOf0(p1)
	TypeOf0(p1).Do()

	TypeOf1(p1)
	pp1 := TypeOf1(p1)
	pp1.Do()

	p2 := &Pointer{}
	Pointer2MyInterface(p2)

	p3 := &Pointer{}
	Pointer2MyInterface(p3).Do()

	p4 := &Pointer{}
	Pointer2MyInterface(p4).(*Pointer).Do()
}
